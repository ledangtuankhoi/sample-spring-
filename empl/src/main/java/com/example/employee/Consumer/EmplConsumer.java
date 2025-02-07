package com.example.employee.Consumer;

import com.example.employee.Constant.KafkaConstants;
import com.example.employee.DTO.EmployeeDTO;
import com.example.employee.Event.BorrowingEvent;
import com.example.employee.Service.BookService;
import com.example.employee.Service.EmployeeService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmplConsumer {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookService bookService;

    private static final Logger logger = LoggerFactory.getLogger(
        EmplConsumer.class
    );

    @KafkaListener(topics = KafkaConstants.EMP_TOPIC, groupId = "book-group")
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

            EmployeeDTO empl = employeeService.getById(
                borrowingEvent.getEmployeeId()
            );

            System.out.println(
                empl.getFirstName() +
                " " +
                empl.getLastName() +
                " staff borrowed a book"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(
        topics = KafkaConstants.BORROWED_BOOK,
        groupId = KafkaConstants.EMPL_GROUP
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
                .getBookById(borrowingEvent.getBookId())
                .getName();
            EmployeeDTO emplName = employeeService.getById(
                borrowingEvent.getEmployeeId()
            );
            System.out.println(
                emplName.getFirstName() +
                " " +
                emplName.getLastName() +
                "staff borrow books " +
                bookName +
                " success"
            );
            //
        } catch (JsonParseException e) {
            System.err.println(
                "json not masmatched, topic: " + KafkaConstants.BORROWED_BOOK
            );
            logger.error(
                "🔥 JSON không hợp lệ! Topic: {} | Message: {}",
                KafkaConstants.BORROWED_BOOK,
                event,
                e
            );

            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
