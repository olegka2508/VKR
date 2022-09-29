package ru.rsreu.app.sevice;

import ru.rsreu.app.utils.io.writer.WriterToDoc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.rsreu.app.model.Quiz;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DownloadService {
    WriterToDoc writer = new WriterToDoc();
    public void downloadQuizListDocx(List<Quiz> quizList, String uuid, HttpServletResponse response) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyyHH-mm-ss");

        Date resultDate = new Date(System.currentTimeMillis());
        String fileName = uuid.concat("").concat(sdf.format(resultDate)).concat(".docx");
        try {
            // get your file as InputStream
            InputStream is = writer.getQuizInputStream(quizList, fileName);
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.setContentType("application/docx");
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
            response.flushBuffer();
            log.info("file with name {} has been downloaded", fileName);
        } catch (IOException ex) {
            log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    public void downloadAnswersDocx(List<Quiz> quizList, String uuid, HttpServletResponse response) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyyHH-mm-ss");

        Date resultDate = new Date(System.currentTimeMillis());
        String fileName = "answers".concat("_").concat(uuid).concat("").concat(sdf.format(resultDate)).concat(".docx");
        try {
            // get your file as InputStream
            InputStream is = writer.getAnswerInputStream(quizList, fileName);
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.setContentType("application/docx");
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
            response.flushBuffer();
            log.info("file with name {} has been downloaded", fileName);
        } catch (IOException ex) {
            log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

}
