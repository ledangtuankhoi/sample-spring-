package com.example.book.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.book.dtos.BookDTO;
import com.example.book.model.BookEntity;
import com.example.book.model.BookModelAssembler;
import com.example.book.model.EmployeeEntity;
import com.example.book.service.BookService;
import com.example.book.service.EmployeeService;
import com.example.book.service.MessageProducer;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TempBookController.class)
class TempBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private MessageProducer messageProducer;

    @MockBean
    private BookModelAssembler bookModelAssembler;

    @MockBean
    private Logger logger;

    @InjectMocks
    private TempBookController tempBookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMessageKafka() throws Exception {
        mockMvc
            .perform(get("/test/book/api/v1/senMessageKafka/Hello"))
            .andExpect(status().isOk());
    }

    @Test
    void testGetEmployee() throws Exception {
        when(employeeService.getEmployeeDetails(1L)).thenReturn(
            new EmployeeEntity()
        );
        mockMvc
            .perform(get("/test/book/api/v1/employee/1"))
            .andExpect(status().isOk());
    }

    @Test
    void testGetEmployeeWithApiGateway() throws Exception {
        when(employeeService.getEmployeeDetailsWithApigetway(2L)).thenReturn(
            new EmployeeEntity()
        );
        mockMvc
            .perform(get("/test/book/api/v1/employeewithapigetway/2"))
            .andExpect(status().isOk());
    }

    @Test
    void testGetAllBooks() throws Exception {
        when(bookService.findAll()).thenReturn(Collections.emptyList());
        mockMvc
            .perform(get("/test/book/api/v1/all"))
            .andExpect(status().isOk());
    }

    @Test
    void testGetAllBooksWithAssembler() throws Exception {
        when(bookService.findAllWithAssembler()).thenReturn(
            CollectionModel.empty()
        );
        mockMvc
            .perform(get("/test/book/api/v1/allWithAssembler"))
            .andExpect(status().isOk());
    }

    @Test
    void testUpdateBook() throws Exception {
        when(bookService.updateBook("1", new BookEntity())).thenReturn(
            new BookEntity()
        );
        mockMvc.perform(
            put("/test/book/api/v1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
        ); // .andExpect(status().isOk())
    }

    @Test
    void testDeleteBook() throws Exception {
        mockMvc
            .perform(delete("/test/book/api/v1/1"))
            .andExpect(status().isNoContent());
    }
}
