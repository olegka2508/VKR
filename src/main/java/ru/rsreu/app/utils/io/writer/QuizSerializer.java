package ru.rsreu.app.utils.io.writer;

import lombok.experimental.UtilityClass;
import ru.rsreu.app.model.Quiz;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

@UtilityClass
public class QuizSerializer {

    public void serialize(List<Quiz> quizList, String fileName) {
        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(quizList);
            objectOutputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
