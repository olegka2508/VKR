package ru.rsreu.app.utils.io.writer;

import ru.rsreu.app.model.QuestionWithEmbodiments;
import ru.rsreu.app.model.Quiz;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;

import java.io.*;
import java.math.BigInteger;
import java.util.List;

public class WriterToDoc implements QuizWriter{

    @Override
    public void writeQuizText(List<Quiz> quizList, String fileName) {
        XWPFDocument document = new XWPFDocument();
        int variant = 1;
        for (Quiz quiz : quizList) {
            variant = fillVariantHeader(document, variant, quiz.getUuid());

            List<QuestionWithEmbodiments> questions = quiz.getQuestions();
            int questionNum = 1;
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun textRun = paragraph.createRun();
            textRun.setFontSize(9);
            textRun.setFontFamily("Times New Roman");
            for (QuestionWithEmbodiments question : questions) {

                textRun.setText("№" + questionNum++ + ". ");

                String questionText = question.getQuestionText();
                textRun.setText(questionText);
                textRun.addBreak();
                List<String> answers = question.getAnswers();
                int answerNum = 1;
                for (String answer : answers) {
                    textRun.setText("       " + intToCharMap(answerNum++) + ". " + answer);
                    textRun.addBreak();
                }
                textRun.addBreak();
            }
            textRun.addBreak(BreakType.PAGE);
        }
        write(fileName, document);
    }

    private String intToCharMap(int i) {
        if (i==1) return "а";
        if (i==2) return "б";
        if (i==3) return "в";
        if (i==4) return "г";
        return "";
    }

    private void setSingleLineSpacing(XWPFParagraph para) {
        para.setIndentFromLeft(-200);
        CTPPr ppr = para.getCTP().getPPr();
        if (ppr == null) ppr = para.getCTP().addNewPPr();
        CTSpacing spacing = ppr.isSetSpacing()? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setAfter(BigInteger.valueOf(0));
        spacing.setBefore(BigInteger.valueOf(0));
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(240));
    }

    @Override
    public void writeQuizAnswers(List<Quiz> quizList, String fileName) {
        XWPFDocument document = new XWPFDocument();
        int variant = 1;
        for (Quiz quiz : quizList) {
            XWPFParagraph paragraphH = document.createParagraph();
            paragraphH.setIndentFromLeft(-200);
            setSingleLineSpacing(paragraphH);
            paragraphH.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun headerRun = paragraphH.createRun();

            headerRun.setFontSize(9);
            headerRun.setBold(true);
            headerRun.setFontFamily("Times New Roman");

            headerRun.setText("Вариант " + variant++);

            XWPFParagraph paragraph = document.createParagraph();
            setSingleLineSpacing(paragraph);
            XWPFRun textRun = paragraph.createRun();
            textRun.setFontSize(8);
            textRun.setFontFamily("Courier New");
//            textRun.addBreak();

            List<QuestionWithEmbodiments> questions = quiz.getQuestions();
            int questionNum = 1;
            for (QuestionWithEmbodiments question : questions) {
                textRun.setText(questionNum++ +"-" + intToCharMap(question.getNumberOfTrueAnswer()) + "; ");
            }
        }
        write(fileName, document);
    }

    private int fillVariantHeader(XWPFDocument document, int variant, String uuid) {
        XWPFParagraph paragraph = document.createParagraph();
//        setSingleLineSpacing(paragraph);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun headerRun = paragraph.createRun();

        headerRun.setFontSize(10);
        headerRun.setBold(true);
        headerRun.setFontFamily("Times New Roman");

        headerRun.setText("Вариант " + variant++);

        return variant;
    }

    private void write(String fileName, XWPFDocument document) {
        try {
            document.write(new FileOutputStream(new File(fileName)));
            System.out.println("Successfully wrote to the " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InputStream getQuizInputStream(List<Quiz> quizList, String fileName) throws IOException {
        XWPFDocument document = new XWPFDocument();
        int variant = 1;
        for (Quiz quiz : quizList) {
            variant = fillVariantHeader(document, variant, quiz.getUuid());

            List<QuestionWithEmbodiments> questions = quiz.getQuestions();
            int questionNum = 1;
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun textRun = paragraph.createRun();
            textRun.setFontSize(9);
            textRun.setFontFamily("Times New Roman");
            for (QuestionWithEmbodiments question : questions) {

                textRun.setText("№" + questionNum++ + ". ");

                String questionText = question.getQuestionText();
                textRun.setText(questionText);
                textRun.addBreak();
                List<String> answers = question.getAnswers();
                int answerNum = 1;
                for (String answer : answers) {
                    textRun.setText("       " + intToCharMap(answerNum++) + ". " + answer);
                    textRun.addBreak();
                }
                textRun.addBreak();
            }
            textRun.addBreak(BreakType.PAGE);
        }
        document.write(new FileOutputStream(new File(fileName)));
        return new FileInputStream(new File(fileName));
    }

    public InputStream getAnswerInputStream(List<Quiz> quizList, String fileName) throws IOException {
        XWPFDocument document = new XWPFDocument();
        int variant = 1;
        for (Quiz quiz : quizList) {
            XWPFParagraph paragraphH = document.createParagraph();
            paragraphH.setIndentFromLeft(-200);
            setSingleLineSpacing(paragraphH);
            paragraphH.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun headerRun = paragraphH.createRun();

            headerRun.setFontSize(9);
            headerRun.setBold(true);
            headerRun.setFontFamily("Times New Roman");

            headerRun.setText("Вариант " + variant++);

            XWPFParagraph paragraph = document.createParagraph();
            setSingleLineSpacing(paragraph);
            XWPFRun textRun = paragraph.createRun();
            textRun.setFontSize(8);
            textRun.setFontFamily("Courier New");
//            textRun.addBreak();

            List<QuestionWithEmbodiments> questions = quiz.getQuestions();
            int questionNum = 1;
            for (QuestionWithEmbodiments question : questions) {
                textRun.setText(questionNum++ +"-" + intToCharMap(question.getNumberOfTrueAnswer()) + "; ");
            }
        }
        document.write(new FileOutputStream(new File(fileName)));
        return new FileInputStream(new File(fileName));
    }
}
