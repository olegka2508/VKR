package ru.rsreu.app.sevice;

import ru.rsreu.app.model.Quiz;

import java.util.List;

public interface QuizRepository {


    List<Quiz> findAllByUserId(long id);

    List<Quiz> findAll();

    List<Quiz> findByNameAndIdContains(String name, long id);

    List<Quiz> findByName(String name);

    List<Quiz> findByNameContains(String name);

    Quiz findByUuid(String uuid);

    void addTest(Quiz quiz, long id);

    void deleteTest(String uuid);
}
