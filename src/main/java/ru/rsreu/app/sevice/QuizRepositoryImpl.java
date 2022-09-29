package ru.rsreu.app.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.rsreu.app.model.Quiz;

import java.util.List;

@Repository
public class QuizRepositoryImpl implements QuizRepository {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public QuizRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Quiz> findAllByUserId(long id) {
        return jdbcTemplate.query("select * from quiz where user_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Quiz.class));
    }

    @Override
    public List<Quiz> findAll() {
        return jdbcTemplate.query("select * from quiz", new Object[]{}, new BeanPropertyRowMapper<>(Quiz.class));
    }

    @Override
    public List<Quiz> findByNameAndIdContains(String name, long id) {
        return jdbcTemplate.query("select * from quiz where name ilike ? and user_id=?", new Object[]{"%" + name + "%",id}, new BeanPropertyRowMapper<>(Quiz.class));
    }

    @Override
    public List<Quiz> findByName(String name) {
        return jdbcTemplate.query("select * from quiz where name=?", new Object[]{name}, new BeanPropertyRowMapper<>(Quiz.class));
    }

    @Override
    public List<Quiz> findByNameContains(String name) {
        return jdbcTemplate.query("select * from quiz where name ilike ?", new Object[]{"%" + name + "%"}, new BeanPropertyRowMapper<>(Quiz.class));
    }

    @Override
    public Quiz findByUuid(String uuid) {
        return jdbcTemplate.queryForObject("select name,uuid from quiz where uuid=?::uuid", new Object[]{uuid}, new BeanPropertyRowMapper<>(Quiz.class));
    }

    @Override
    public void addTest(Quiz quiz, long id) {
        jdbcTemplate.update("insert into quiz (uuid,name,user_id) values (?::uuid,?,?)", quiz.getUuid(), quiz.getName(), id);
    }

    @Override
    public void deleteTest(String uuid) {
        jdbcTemplate.update("delete from quiz where uuid=?::uuid ", uuid);
    }
}

