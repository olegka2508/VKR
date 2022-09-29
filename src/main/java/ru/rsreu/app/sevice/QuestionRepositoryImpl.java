package ru.rsreu.app.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.rsreu.app.model.QuestionWithEmbodiments;
import ru.rsreu.app.model.Quiz;

import java.sql.Timestamp;
import java.util.*;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public QuestionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveGeneratedQuestions(List<QuestionWithEmbodiments> questions, String uuid, String variantUuid, Date date) {
        for (QuestionWithEmbodiments question : questions) {
            List<String> answers = question.getAnswers();
            jdbcTemplate.update("insert into generated_question (question_text,quiz_uuid,answer1,answer2,answer3,answer4,number_of_true_answer,variant_uuid,date_of_generation) " +
                    "values (?,?::uuid,?,?,?,?,?,?::uuid,?)", question.getQuestionText(), uuid, answers.get(0), answers.get(1), answers.get(2), answers.get(3), question.getNumberOfTrueAnswer(), variantUuid, date);
        }
    }

    @Override
    public List<Quiz> findByUuidInGenerated(String uuid, Timestamp time) {

        List<Quiz> quizList = new ArrayList<>();
        LinkedHashMap<String, List<QuestionWithEmbodiments>> map = new LinkedHashMap<>();
        jdbcTemplate.query("select question_text,variant_uuid,answer1,answer2,answer3,answer4,number_of_true_answer from generated_question where quiz_uuid=?::uuid and date_of_generation=?",
                rs -> {

                    while (rs.next()) {
                        QuestionWithEmbodiments question = new QuestionWithEmbodiments();
                        question.setQuestionText(rs.getString("question_text"));
                        question.setAnswers(Arrays.asList(rs.getString("answer1"), rs.getString("answer2"), rs.getString("answer3"), rs.getString("answer4")));
                        question.setNumberOfTrueAnswer(rs.getInt("number_of_true_answer"));
                        String variant_uuid = rs.getString("variant_uuid");
                        if (map.get(variant_uuid) != null) {
                            map.get(variant_uuid).add(question);
                        } else {
                            List<QuestionWithEmbodiments> list = new ArrayList<>();
                            list.add(question);
                            map.put(variant_uuid, list);
                        }
                    }
                    return null;
                }, uuid, time);
        for (Map.Entry<String, List<QuestionWithEmbodiments>> entry : map.entrySet()) {
            Quiz quiz = new Quiz();
            quiz.getQuestions().addAll(entry.getValue());
            quizList.add(quiz);
        }
        return quizList;
    }

    @Override
    public void addQuestion(QuestionWithEmbodiments question, String uuid) {
        List<String> answers = question.getAnswers();
        jdbcTemplate.update("insert into question (question_text,quiz_uuid,answer1,answer2,answer3,answer4,number_of_true_answer) " +
                "values (?,?::uuid,?,?,?,?,?)", question.getQuestionText(), uuid, answers.get(0), answers.get(1), answers.get(2), answers.get(3), question.getNumberOfTrueAnswer());
    }

    @Override
    public void deleteSeveral(List<QuestionWithEmbodiments> questions, String uuid) {
        for (QuestionWithEmbodiments question : questions) {
            jdbcTemplate.update("delete from question where quiz_uuid=?::uuid and question_text = ?", uuid, question.getQuestionText());
        }
    }

    @Override
    public void deleteAllUuid(String uuid) {
        jdbcTemplate.update("delete from question where quiz_uuid=?::uuid", uuid);
    }

    @Override
    public void editQuestion(QuestionWithEmbodiments newQuestion, QuestionWithEmbodiments oldQuestion, String uuid) {
        List<String> answers = newQuestion.getAnswers();
        jdbcTemplate.update("update question set question_text=?,answer1=?,answer2=?,answer3=?,answer4=?,number_of_true_answer=? where quiz_uuid=?::uuid and question_text=?",
                newQuestion.getQuestionText(), answers.get(0), answers.get(1), answers.get(2), answers.get(3), newQuestion.getNumberOfTrueAnswer(), uuid, oldQuestion.getQuestionText());
    }

    @Override
    public List<QuestionWithEmbodiments> findByUuid(String uuid) {
        List<QuestionWithEmbodiments> questions = new ArrayList<>();
        jdbcTemplate.query("select question_text, answer1,answer2,answer3,answer4,number_of_true_answer from question where quiz_uuid=?::uuid",
                rs -> {
                    while (rs.next()) {
                        QuestionWithEmbodiments question = new QuestionWithEmbodiments();
                        question.setQuestionText(rs.getString("question_text"));
                        question.setAnswers(Arrays.asList(rs.getString("answer1"), rs.getString("answer2"), rs.getString("answer3"), rs.getString("answer4")));
                        question.setNumberOfTrueAnswer(rs.getInt("number_of_true_answer"));
                        questions.add(question);
                    }
                    return null;
                }, uuid);

        return questions;
    }

    @Override
    public List<Timestamp> findTimestampByUuidFromGeneratedQuestion(String uuid) {
        return jdbcTemplate.queryForList("select distinct date_of_generation from generated_question where quiz_uuid=?::uuid order by date_of_generation desc", Timestamp.class, uuid);
    }

    @Override
    public Integer getCountGeneratedVariantByUuid(String uuid, Timestamp timestamp) {
        return jdbcTemplate.queryForObject("select count(distinct variant_uuid) from generated_question where quiz_uuid = ?::uuid" +
                "  and date_of_generation = ?", Integer.class, uuid, timestamp);
    }

    @Override
    public Integer getCountQuestionInGeneratedVariantByUuid(String uuid, Timestamp timestamp) {
        return jdbcTemplate.queryForObject("select count(*)\n" +
                "from generated_question " +
                "where quiz_uuid = ?::uuid" +
                "  and date_of_generation = ?" +
                "  and variant_uuid = (select variant_uuid " +
                "                      from generated_question " +
                "                      where quiz_uuid = ?::uuid" +
                "                        and date_of_generation = ? " +
                "                      limit 1)", Integer.class, uuid, timestamp, uuid, timestamp);
    }

    @Override
    public void delete(String questionText, String uuid) {
        jdbcTemplate.update("delete from question where quiz_uuid=?::uuid and question_text = ?", uuid, questionText);
    }
}
