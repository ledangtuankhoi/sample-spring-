package com.example.employee.Service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "my-topic", groupId = "book-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
    