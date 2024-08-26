package com.ftn.isa.service.impl;

import com.ftn.isa.dto.email.EmailDetailsDTO;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendEmailWithAttachment(EmailDetailsDTO details, String qrCodeImage);

    void sendEmail(EmailDetailsDTO email);
}
