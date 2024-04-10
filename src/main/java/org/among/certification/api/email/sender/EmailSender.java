package org.among.certification.api.email.sender;

import org.springframework.mail.javamail.JavaMailSender;

public interface EmailSender {
    JavaMailSender mailSender();
}
