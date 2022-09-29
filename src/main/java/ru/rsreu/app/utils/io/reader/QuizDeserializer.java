package ru.rsreu.app.utils.io.reader;

import ru.rsreu.app.model.Quiz;
import lombok.experimental.UtilityClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

@UtilityClass
public class QuizDeserializer {
    public List<Quiz> deserialize(String fileName) {
        List<Quiz> quizList = null;
        try {
            FileInputStream inputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            quizList = (List<Quiz>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return quizList;
    }
}
