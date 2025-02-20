package com.example.book.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.book.controller.BookController;
import com.example.book.mappers.BookMapper;
import com.example.book.model.BookEntity;
import com.example.book.model.BookModelAssembler;
import com.example.book.repository.BookRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class) // Chỉ test controller
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc; // Gửi request HTTP mô phỏng

    @MockBean
    private BookRepository repository; // Giả lập repository

    @MockBean
    private BookModelAssembler assembler; // Giả lập assembler

    @MockBean
    private BookMapper bookMapper;

    Logger logger = LoggerFactory.getLogger(BookControllerTest.class);
}
