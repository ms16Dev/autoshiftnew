package com.example.demo.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final String fromEmail = "fs.vue.info@gmail.com";

    public Mono<Void> sendVerificationEmail(String toEmail, String token) {
        return Mono.fromRunnable(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Verify Your Email Address");
            message.setText("Please verify your email by clicking this link: https://localhost:3000/auth/verify?token=" + token);
            mailSender.send(message);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }
}
