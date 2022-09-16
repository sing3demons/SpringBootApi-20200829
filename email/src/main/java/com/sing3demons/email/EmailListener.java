package com.sing3demons.email;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class EmailListener {
    @KafkaListener(topics = "activation-email")
    public void listenForActivationEmail(String message) {
        log.info("Kafka received: " + message);
    }
}
