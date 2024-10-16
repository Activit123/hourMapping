package com.mihai.Java_2024.features.resetEmailFeature.email.controller;


import com.mihai.Java_2024.features.resetEmailFeature.email.Email;
import com.mihai.Java_2024.features.resetEmailFeature.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
@Slf4j
public class EmailController {
    private final EmailService emailService;
    @PostMapping
    public void sendEmail(@RequestBody Email email) {
        log.info(emailService.sendSimpleEmail(email));
    }
}
