package com.sing3demons.email.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.sing3demons.common.EmailRequest;
import com.sing3demons.email.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class EmailListener {
    private final EmailService emailService;

    @KafkaListener(topics = "activation-email")
    public void listenForActivationEmail(EmailRequest request) {
        log.info("Kafka received: " + request.getTo());

        emailService.send(request.getTo(), request.getSubject(), request.getContent());
    }
}
