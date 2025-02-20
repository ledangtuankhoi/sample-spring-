package com.example.book.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "my-topic", groupId = "book-group")
    public void listen(String message) {
        System.out.println("book ---- Received message: " + message);
    }
}
