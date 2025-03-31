package com.example.book.consumer;

import com.example.book.constant.KafkaConstants;
import com.example.book.event.BorrowingEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BookConsumer {

    @Autowired
    private ObjectMapper mapper;

    Logger logger = LoggerFactory.getLogger(BookConsumer.class);

    @KafkaListener(topics = KafkaConstants.BOOK_TOPIC, groupId = "book-group")
    public void listen(String event) {
        try {
            BorrowingEvent borrowingEvent = mapper.readValue(
                event,
                BorrowingEvent.class
            );
            logger.info("book ---- Received message: {0}", borrowingEvent);
        } catch (Exception e) {
            logger.error("context", e);
        }
    }

    @KafkaListener(
        topics = KafkaConstants.BORROWED_BOOK,
        groupId = KafkaConstants.BOOK_GROUP
    )
    public void listenBorrowedBook(String event) {
        try {
            BorrowingEvent borrowingEvent = mapper.readValue(
                event,
                BorrowingEvent.class
            );
            logger.info("book ---- Received message: {0}", borrowingEvent);
        } catch (Exception e) {
            logger.error("context", e);
        }
    }
}
