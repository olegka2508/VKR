package ru.rsreu.app.controller;

import ru.rsreu.app.dto.DownloadParamDto;
import ru.rsreu.app.dto.GeneratedInfoDto;
import ru.rsreu.app.dto.QuestionDto;
import ru.rsreu.app.dto.SearchTextDto;
import ru.rsreu.app.entity.Role;
import ru.rsreu.app.entity.User;
import ru.rsreu.app.model.QuestionWithEmbodiments;
import ru.rsreu.app.model.Quiz;
import ru.rsreu.app.sevice.GenerationService;
import ru.rsreu.app.sevice.QuestionRepository;
import ru.rsreu.app.sevice.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final GenerationService generationService;

    @Autowired
    public QuizController(QuestionRepository questionRepository, QuizRepository quizRepository, GenerationService generationService) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.generationService = generationService;
    }


    @GetMapping("/findAll")
    public String getAllTests(Model model, Authentication authentication) {
        if (isAdmin(((User) authentication.getPrincipal()).getRoles())) {
            model.addAttribute("tests", quizRepository.findAll());
        } else {
            Long userId = getIdFromAuth(authentication);
            model.addAttribute("tests", quizRepository.findAllByUserId(userId));
        }
        model.addAttribute("searchtextdto", new SearchTextDto());
        return "findAll";
    }

    private boolean isAdmin(Set<Role> roles) {
        for (Role role : roles) {
            if (role.getName().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

    private Long getIdFromAuth(Authentication authentication) {
        return ((User) authentication.getPrincipal()).getId();
    }

    @PostMapping("/findByName")
    public String getNameTest(Model model, @ModelAttribute SearchTextDto searchTextDto, Authentication authentication) {
        if (isAdmin(((User) authentication.getPrincipal()).getRoles())) {
            model.addAttribute("tests", quizRepository.findByNameContains(searchTextDto.getText()));
        } else {
            Long userId = getIdFromAuth(authentication);
            model.addAttribute("tests", quizRepository.findByNameAndIdContains(searchTextDto.getText(), userId));
        }
        return "findByName";
    }

    @GetMapping(value = "/addTest")
    public String addTest(Model model) {
        Quiz newTest = new Quiz();
        model.addAttribute("newTest", newTest);
        return "addTest";
    }

    @GetMapping(value = "/checkQuestions/{uuid}")
    public String showFormQuestion(@PathVariable(value = "uuid") String uuid, Model model) {
        Quiz quiz = quizRepository.findByUuid(uuid);
        quiz.getQuestions().addAll(questionRepository.findByUuid(uuid));
        model.addAttribute("test", quiz);
        return "checkQuestions";
    }

    @GetMapping("/saveQuestion/{uuid}")
    public String saveQuestion(@PathVariable(value = "uuid") String uuid, @ModelAttribute QuestionDto question, Model model) {
        Quiz quiz = quizRepository.findByUuid(uuid);
        QuestionWithEmbodiments addQuestion = new QuestionWithEmbodiments();
        addQuestion.setQuestionText(question.getQuestionText());
        addQuestion.getAnswers().add(question.getAnswer0());
        addQuestion.getAnswers().add(question.getAnswer1());
        addQuestion.getAnswers().add(question.getAnswer2());
        addQuestion.getAnswers().add(question.getAnswer3());
        addQuestion.setNumberOfTrueAnswer(question.getNumberOfTrueAnswer());
        questionRepository.addQuestion(addQuestion, uuid);
        QuestionDto newQuestion = new QuestionDto();
        quiz.getQuestions().addAll(questionRepository.findByUuid(uuid));
        model.addAttribute("question", newQuestion);
        model.addAttribute("test", quiz);
        return "editQuestion";
    }

    @PostMapping("/saveTest")
    public String saveTest(@ModelAttribute Quiz newTest, Model model, Authentication authentication) {
        Long userId = getIdFromAuth(authentication);
        quizRepository.addTest(newTest, userId);
        model.addAttribute("test", newTest);
        return "redirect:/api/quiz/addQuestion/" + newTest.getUuid();
    }

    @GetMapping("/addQuestion/{uuid}")
    public String addQuestion(@PathVariable(value = "uuid") String uuid, Model model) {
        Quiz quiz = quizRepository.findByUuid(uuid);
        QuestionDto question = new QuestionDto();
        quiz.getQuestions().addAll(questionRepository.findByUuid(uuid));
        model.addAttribute("question", question);
        model.addAttribute("test", quiz);
        return "editQuestion";
    }


    @GetMapping("/deleteQuestion/{uuid}/{questionText}")
    public String deleteQuestion(@PathVariable(value = "uuid") String uuid, @PathVariable(value = "questionText") String questionText) {
        questionRepository.delete(questionText, uuid);
        return "redirect:/api/quiz/addQuestion/" + uuid;
    }

    @GetMapping("/delete/{uuid}")
    public String delete(@PathVariable(value = "uuid") String uuid) {
        quizRepository.deleteTest(uuid);
        return "redirect:/api/quiz/findAll";
    }

    @GetMapping("/download/{uuid}")
    public String download(@PathVariable(value = "uuid") String uuid, Model model) {
        Quiz quiz = quizRepository.findByUuid(uuid);
        DownloadParamDto param = new DownloadParamDto();
        List<Timestamp> generatedTimestampList = questionRepository.findTimestampByUuidFromGeneratedQuestion(uuid);
        List<GeneratedInfoDto> generatedInfoDtoList = new ArrayList<>();
        for (Timestamp timestamp : generatedTimestampList) {
            GeneratedInfoDto generatedInfoDto = new GeneratedInfoDto();
            generatedInfoDto.setGeneratedTimestamp(timestamp);
            generatedInfoDto.setCountOfQuestionInVar(questionRepository.getCountQuestionInGeneratedVariantByUuid(uuid, timestamp));
            generatedInfoDto.setCountOfVariant(questionRepository.getCountGeneratedVariantByUuid(uuid, timestamp));
            generatedInfoDtoList.add(generatedInfoDto);
        }
        model.addAttribute("generatedInfoList", generatedInfoDtoList);
        model.addAttribute("test", quiz);
        model.addAttribute("param", param);
        return "download";
    }

    @GetMapping("/generationTest/{uuid}")
    public String generationTest(@PathVariable("uuid") String uuid, @ModelAttribute DownloadParamDto param) {
        List<QuestionWithEmbodiments> originalQuestions = questionRepository.findByUuid(uuid);
        Quiz quiz = new Quiz();
        quiz.getQuestions().addAll(originalQuestions);
        quiz.setUuid(uuid);
        List<Quiz> generatedQuizList = generationService.generate(quiz, param.getVariantLimit(), param.getQuestionLimit());
        Date date = new Date();
        for (Quiz generatedQuiz : generatedQuizList) {
            String variantUuid = generatedQuiz.getUuid();
            questionRepository.saveGeneratedQuestions(generatedQuiz.getQuestions(), uuid, variantUuid, date);
        }
        return "redirect:/api/quiz/download/" + uuid;
    }
}