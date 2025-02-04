package com.example.employee.Consumer;

import com.example.employee.Constant.KafkaConstants;
import com.example.employee.DTO.EmployeeDTO;
import com.example.employee.Event.BorrowingEvent;
import com.example.employee.Model.BorrowingEntity;
import com.example.employee.Model.EmployeeEntity;
import com.example.employee.Service.BookService;
import com.example.employee.Service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BookConsumer {

    @Autowired
    private EmployeeService employeeService;

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
}
