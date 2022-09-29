package ru.rsreu.app.sevice;

import ru.rsreu.app.model.QuestionWithEmbodiments;
import ru.rsreu.app.model.Quiz;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface QuestionRepository {


    List<Quiz> findByUuidInGenerated(String uuid, Timestamp time);

    void addQuestion(QuestionWithEmbodiments question, String uuid);

    void deleteSeveral(List<QuestionWithEmbodiments> questions, String uuid);

    void deleteAllUuid(String uuid);

    void editQuestion(QuestionWithEmbodiments newQuestion, QuestionWithEmbodiments oldQuestion, String uuid);

    List<QuestionWithEmbodiments> findByUuid(String text);

    List<Timestamp> findTimestampByUuidFromGeneratedQuestion(String uuid);

    Integer getCountGeneratedVariantByUuid(String uuid, Timestamp timestamp);

    Integer getCountQuestionInGeneratedVariantByUuid(String uuid, Timestamp timestamp);

    void delete(String questionText, String uuid);

    void saveGeneratedQuestions(List<QuestionWithEmbodiments> questions, String uuid, String variantUuid, Date date);

}
