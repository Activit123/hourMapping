package com.mihai.Java_2024.features.resetEmailFeature.email.service;

import com.mihai.Java_2024.features.resetEmailFeature.email.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final String sender = "no-reply@auction.info";

    @Override
    public String sendSimpleEmail(Email email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            //the message will be constructed in the reset email branch
            message.setFrom(sender);
            message.setTo(email.getRecipient());
            message.setText(email.getMessage());
            message.setSubject(email.getSubject());
            log.info(message.toString());
            javaMailSender.send(message);
            return "Email sent successfully";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "Error sending email";
        }
    }
}
