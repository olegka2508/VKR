package ru.rsreu.app.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
public class Quiz implements Serializable {
    private String name;
    private List<QuestionWithEmbodiments> questions = new ArrayList<>();
    private String uuid = UUID.randomUUID().toString();

    public void addNewQuestion(QuestionWithEmbodiments question) {
        this.questions.add(question);
    }
}
