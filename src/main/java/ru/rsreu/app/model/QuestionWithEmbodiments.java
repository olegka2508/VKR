package ru.rsreu.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionWithEmbodiments implements Serializable {
    @JsonProperty(value = "question_text")
    private String questionText;
    private List<String> answers = new ArrayList<>();
    @JsonProperty(value = "number_of_true_answer")
    private Integer numberOfTrueAnswer;

    public int getIndexOfTrueAnswer() {
        return this.numberOfTrueAnswer - 1;
    }
}
