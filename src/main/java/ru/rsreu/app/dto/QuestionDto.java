package ru.rsreu.app.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionDto {
    String questionText;
    String answer0;
    String answer1;
    String answer2;
    String answer3;
    Integer numberOfTrueAnswer;

}
