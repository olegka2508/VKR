/*
package ru.rsreu.cookbook.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;

@UtilityClass
public class QrCodeUtil {

    private void createQR(String data,
                                String path,
                                String charset,
                                int height, int width)
            throws WriterException, IOException {

        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, width, height);

        MatrixToImageWriter.writeToFile(
                matrix,
                path.substring(path.lastIndexOf('.') + 1),
                new File(path));
    }

    public void writeQRToFile(String data, String fileName) {
        String charset = "UTF-8";
        try {
            createQR(data, fileName, charset, 200, 200);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("QR Code Generated.");
    }
}
*/
