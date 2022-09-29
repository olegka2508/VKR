package ru.rsreu.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
public class GeneratedInfoDto {
    Timestamp generatedTimestamp;
    Integer countOfVariant;
    Integer countOfQuestionInVar;
}
