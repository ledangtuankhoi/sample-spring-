package com.example.book.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    Logger logger = LoggerFactory.getLogger(getClass());

    @KafkaListener(topics = "my-topic", groupId = "book-group")
    public void listen(String message) {
        logger.info("book ---- Received message: " + message);
    }
}
