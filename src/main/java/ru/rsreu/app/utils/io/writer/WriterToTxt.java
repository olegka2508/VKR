package ru.rsreu.app.utils.io.writer;

import ru.rsreu.app.model.QuestionWithEmbodiments;
import ru.rsreu.app.model.Quiz;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriterToTxt implements QuizWriter{

    @Override
    public void writeQuizText(List<Quiz> quizList, String fileName) {
        try {
            FileWriter myWriter = new FileWriter(fileName);
            int variant = 1;
            for (Quiz quiz : quizList) {
                variant = fillVariantHeader(myWriter, variant, quiz.getUuid());
                List<QuestionWithEmbodiments> questions = quiz.getQuestions();
                int questionNum = 1;
                for (QuestionWithEmbodiments question : questions) {
                    myWriter.write("Вопрос №" + questionNum++ + "\n");
                    String questionText = question.getQuestionText();
                    myWriter.write(questionText + "\n");
                    List<String> answers = question.getAnswers();
                    int answerNum = 1;
                    for (String answer : answers) {
                        myWriter.write(answerNum++ + ". " + answer + "\n");
                    }
                    myWriter.write("\n");
                }
                myWriter.write("\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void writeQuizAnswers(List<Quiz> quizList, String fileName) {
        try {
            FileWriter myWriter = new FileWriter(fileName);
            int variant = 1;
            for (Quiz quiz : quizList) {
                variant = fillVariantHeader(myWriter, variant, quiz.getUuid());
                List<QuestionWithEmbodiments> questions = quiz.getQuestions();
                int questionNum = 1;
                for (QuestionWithEmbodiments question : questions) {
                    myWriter.write("Вопрос №" + questionNum++ + " - " + question.getNumberOfTrueAnswer());
                    myWriter.write("\n");
                }
                myWriter.write("\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private int fillVariantHeader(FileWriter myWriter, int variant, String uuid) throws IOException {
        myWriter.write("----------------------------------------------------" + "\n");
        myWriter.write("Вариант " + variant++ + "\n");
        myWriter.write(uuid + "\n");
        myWriter.write("\n");
        return variant;
    }
}
