package ru.rsreu.app.dto;

import ru.rsreu.app.model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class TestsCreationDto {
    private List<Quiz> tests;

    public TestsCreationDto() {
        this.tests = new ArrayList<>();
    }

    public TestsCreationDto(List<Quiz> tests) {
        this.tests = tests;
    }

    public List<Quiz> getTests() {
        return tests;
    }

    public void setTests(List<Quiz> tests) {
        this.tests = tests;
    }

    public void addQuiz(Quiz quiz) {
        this.tests.add(quiz);
    }
}
