package ru.rsreu.app.controller;

import ru.rsreu.app.sevice.DownloadService;
import ru.rsreu.app.sevice.GenerationService;
import ru.rsreu.app.sevice.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.rsreu.app.model.Quiz;
import ru.rsreu.app.sevice.QuizRepository;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/api/quiz")
public class DownloadController {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final GenerationService generationService;
    private final DownloadService downloadService;


    @Autowired
    public DownloadController(QuestionRepository questionRepository, QuizRepository quizRepository, GenerationService generationService, DownloadService downloadService) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.generationService = generationService;
        this.downloadService = downloadService;
    }


    @GetMapping("/files/docx/questions/{uuid}/{timestamp}")
    public void getQuestionDocxFile(@PathVariable("uuid") String uuid, @PathVariable("timestamp") Timestamp timestamp,
                                    HttpServletResponse response) {
        List<Quiz> generatedQuizList = questionRepository.findByUuidInGenerated(uuid, timestamp);
        downloadService.downloadQuizListDocx(generatedQuizList, uuid, response);
    }

    @GetMapping("/files/docx/answers/{uuid}/{timestamp}")
    public void getAnswerFile(@PathVariable("uuid") String uuid, @PathVariable("timestamp") Timestamp timestamp,
                              HttpServletResponse response) {
        List<Quiz> generatedQuizList = questionRepository.findByUuidInGenerated(uuid, timestamp);
        downloadService.downloadAnswersDocx(generatedQuizList, uuid, response);
    }
}