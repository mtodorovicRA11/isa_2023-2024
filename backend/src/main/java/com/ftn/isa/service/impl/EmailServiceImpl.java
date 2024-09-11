package com.ftn.isa.service.impl;

import com.ftn.isa.dto.email.EmailDetailsDTO;
import com.ftn.isa.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.Base64;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public void sendEmailWithAttachment(EmailDetailsDTO details, String qrCodeImage) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(details.getMessageBody(), "text/html; charset=UTF-8");

            Multipart multiPart = new MimeMultipart("alternative");

            byte[] rawImage = Base64.getDecoder().decode(qrCodeImage);
            BodyPart imagePart = new MimeBodyPart();
            ByteArrayDataSource imageDataSource = new ByteArrayDataSource(rawImage, "image/png");

            imagePart.setDataHandler(new DataHandler(imageDataSource));
            imagePart.setHeader("Content-ID", "<qrImage>");
            imagePart.setFileName("QRCode.png");

            multiPart.addBodyPart(htmlPart);
            multiPart.addBodyPart(imagePart);

            message.setContent(multiPart);
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setFrom(sender);
            helper.setTo(details.getRecipient());
            helper.setSubject(details.getSubject());
            javaMailSender.send(message);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void sendEmail(EmailDetailsDTO email) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        try {
            helper.setFrom(sender);
            helper.setTo(email.getRecipient());
            helper.setText(email.getMessageBody(), true);
            helper.setSubject(email.getSubject());
            javaMailSender.send(message);

        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
    }
}
