package ru.rsreu.app.utils.io.writer;

import ru.rsreu.app.model.Quiz;

import java.util.List;

public interface QuizWriter {
    void writeQuizText(List<Quiz> quizList, String fileName);

    void writeQuizAnswers(List<Quiz> quizList, String fileName);
}
