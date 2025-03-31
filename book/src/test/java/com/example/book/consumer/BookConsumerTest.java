package com.example.book.consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.example.book.constant.KafkaConstants;
import com.example.book.dtos.BookDTO;
import com.example.book.event.BorrowingEvent;
import com.example.book.model.BookEntity;
import com.example.book.model.EmployeeEntity;
import com.example.book.service.BookService;
import com.example.book.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

@ExtendWith(MockitoExtension.class)
class BookConsumerTest {

    @Mock
    private BookService bookService;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private Logger logger;

    @InjectMocks
    private BookConsumer bookConsumer;

    private BorrowingEvent borrowingEvent;
    private EmployeeEntity employeeEntity;

    @BeforeEach
    void setUp() {
        borrowingEvent = new BorrowingEvent();
        borrowingEvent.setBookId("1");
        borrowingEvent.setEmployeeId("123");

        employeeEntity = new EmployeeEntity();
        employeeEntity.setFirstName("John");
        employeeEntity.setLastName("Doe");
    }

    @Test
    void listen_Exception() throws Exception {
        // Thiết lập hành vi của mapper để ném ra ngoại lệ
        when(mapper.readValue(anyString(), eq(BorrowingEvent.class))).thenThrow(
            new RuntimeException("JSON parsing error")
        );

        // Gọi phương thức listen và kiểm tra log lỗi
        bookConsumer.listen("test event");

        // Kiểm tra các phương thức của mapper và logger được gọi đúng số lần
        verify(mapper, times(1)).readValue(
            anyString(),
            eq(BorrowingEvent.class)
        );
        verify(logger, times(1)).error(
            eq("context"),
            any(RuntimeException.class)
        );
    }

    @Test
    void listen_Success() throws Exception {
        // Thiết lập hành vi của mapper và bookService
        when(
            mapper.readValue(anyString(), eq(BorrowingEvent.class))
        ).thenReturn(borrowingEvent);

        // Gọi phương thức listen và kiểm tra log
        bookConsumer.listen("test event");

        // Kiểm tra các phương thức của mapper và bookService được gọi đúng số lần
        verify(mapper, times(1)).readValue(
            anyString(),
            eq(BorrowingEvent.class)
        );
        verify(logger, times(1)).info(
            eq("book ---- Received message: {0}"),
            any(BorrowingEvent.class)
        );
    }

    @Test
    void listenBorrowedBook_Success() throws Exception {
        // Thiết lập hành vi của mapper, bookService và employeeService
        when(
            mapper.readValue(anyString(), eq(BorrowingEvent.class))
        ).thenReturn(borrowingEvent);

        // Gọi phương thức listenBorrowedBook và kiểm tra log
        bookConsumer.listenBorrowedBook("test event");

        // Kiểm tra các phương thức của mapper, bookService và employeeService được gọi đúng số lần
        verify(mapper, times(1)).readValue(
            anyString(),
            eq(BorrowingEvent.class)
        );
        verify(logger, times(1)).info(
            eq("book ---- Received message: {0}"),
            any(BorrowingEvent.class)
        );
    }

    @Test
    void listenBorrowedBook_Exception() throws Exception {
        // Thiết lập hành vi của mapper để ném ra ngoại lệ
        when(mapper.readValue(anyString(), eq(BorrowingEvent.class))).thenThrow(
            new RuntimeException("JSON parsing error")
        );

        // Gọi phương thức listenBorrowedBook và kiểm tra log lỗi
        bookConsumer.listenBorrowedBook("test event");

        // Kiểm tra các phương thức của mapper và logger được gọi đúng số lần
        verify(mapper, times(1)).readValue(
            anyString(),
            eq(BorrowingEvent.class)
        );
        verify(logger, times(1)).error(
            eq("context"),
            any(RuntimeException.class)
        );
    }
}
