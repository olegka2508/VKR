package ru.rsreu.app.sevice;

import org.springframework.stereotype.Service;
import ru.rsreu.app.model.Quiz;
import ru.rsreu.app.utils.QuizCombinatorialUtil;

import java.util.List;

@Service
public class GenerationService {
    public List<Quiz> generate(Quiz quiz, int variantLimit, int questionLimit) {
        return QuizCombinatorialUtil.generate(quiz, variantLimit, questionLimit);
    }
}
