package com.example.book.Consumer;

import com.example.book.Constant.KafkaConstants;
import com.example.book.Event.BorrowingEvent;
import com.example.book.Model.BorrowingEntity;
import com.example.book.Model.EmployeeEntity;
import com.example.book.Service.BookService;
import com.example.book.Service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BookConsumer {

    @Autowired
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeService employeeService;

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

    @KafkaListener(
        topics = KafkaConstants.BORROWED_BOOK,
        groupId = KafkaConstants.BOOK_GROUP
    )
    public void listenBorrowedBook(String event) {
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
            EmployeeEntity emplName =
                employeeService.getEmployeeDetailsWithApigetway(
                    Long.parseLong(borrowingEvent.getEmployeeId())
                );
            System.out.println(
                "book " +
                bookName +
                " successfully borrowed by " +
                emplName.getFirstName() +
                emplName.getLastName() +
                " staff"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
