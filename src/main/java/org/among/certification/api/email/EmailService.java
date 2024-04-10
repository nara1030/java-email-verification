package org.among.certification.api.email;

import org.among.certification.api.email.sender.EmailSender;
import org.among.certification.domain.verify.dto.VerifyRequest;
import org.among.certification.util.UuidGenerator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final String MAIL_CONTENT_TEMPLATE = "http://localhost:8080/v1/verify-email?email=%s&uuid=%s";
    private final JavaMailSender emailSender;

    public EmailService(EmailSender emailSender) {
        this.emailSender = emailSender.mailSender();
    }

    public String send(String email) {
        SimpleMailMessage senderConfig = new SimpleMailMessage();
        senderConfig.setTo(email);
        senderConfig.setSubject("Test 앱에서 보내는 인증 메일입니다.");
        String uuid = UuidGenerator.uuidGenerator();
        String content = String.format(MAIL_CONTENT_TEMPLATE, email, uuid);
        senderConfig.setText(content);
        emailSender.send(senderConfig);

        return uuid;
    }
}
