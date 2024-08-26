package com.ftn.isa.service.impl;

import com.ftn.isa.model.Reservation;
import com.ftn.isa.model.User;
import com.ftn.isa.service.QRCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;

@Service
public class QRCodeServiceImpl implements QRCodeService {
    Logger logger = LoggerFactory.getLogger(QRCodeServiceImpl.class);

    public String generateQRCode(User user, Reservation reservation) {
        String userText = user.getFirstName() + ' ' + user.getLastName();
        String companyText = reservation.getCompany().getName();
        String dateText = reservation.getStartTime().toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        String timeText = reservation.getStartTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        String durationText = String.valueOf(reservation.getDuration());

        String qrCodeText = "Reservation: \n-User: " + userText + "\n-Company: " + companyText + "\n-Date: " + dateText + "\n-Time: " + timeText +
                "\n-Duration: " + durationText + " minutes";

        byte[] image = getQRCodeImage(qrCodeText, 250, 250);

        return Base64.getEncoder().encodeToString(image);
    }

    public byte[] getQRCodeImage(String text, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        HashMap<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig config = new MatrixToImageConfig(0xFF000000, 0xFFFFFFFF);

        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, config);
        } catch (WriterException | IOException e) {
            logger.error(e.getMessage());
        }

        return pngOutputStream.toByteArray();
    }
}
