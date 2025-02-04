package com.example.book.Consumer;

import com.example.book.Constant.KafkaConstants;
import com.example.book.Event.BorrowingEvent;
import com.example.book.Model.BorrowingEntity;
import com.example.book.Service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BookConsumer {

    @Autowired
    private BookService bookService;

    @KafkaListener(topics = KafkaConstants.BOOK_TOPIC, groupId = "book-group")
    public void listen(String event) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            BorrowingEvent borrowingEvent = objectMapper.readValue(
                event,
                BorrowingEvent.class
            );
            System.out.println(
                "book ---- Received message: " +
                objectMapper.readValue(event, BorrowingEvent.class)
            );

            String bookName = bookService
                .getById(borrowingEvent.getBookId())
                .getName();
            System.out.println(
                "This " + bookName + " book was borrowed by this employee."
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
