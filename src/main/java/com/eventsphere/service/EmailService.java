package com.eventsphere.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    public void send(String to, String subject, String body){
        try{
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(body);
            mailSender.send(msg);
        }catch (Exception ex){
            // Fail softly in dev
            log.warn("Email send skipped/failure: {}", ex.getMessage());
        }
    }
}
