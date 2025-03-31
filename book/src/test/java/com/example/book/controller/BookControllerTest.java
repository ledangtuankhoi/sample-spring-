package com.example.book.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.book.dtos.BookDTO;
import com.example.book.model.BookEntity;
import com.example.book.service.BookService;
import java.util.Collections; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks; 
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() throws Exception {
        when(bookService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/book/api/v1/all")).andExpect(status().isOk());
    }

    @Test
    void testGetBook() throws Exception {
        when(bookService.findById("1")).thenReturn(new BookEntity());
        mockMvc.perform(get("/book/api/v1/1")).andExpect(status().isOk());
    }

    @Test
    void testCreateBook() throws Exception {
        when(bookService.save(new BookDTO())).thenReturn(new BookEntity());
        mockMvc
            .perform(
                post("/book/api/v1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}")
            )
            .andExpect(status().isOk());
    }

    @Test
    void testUpdateBook() throws Exception {
        when(bookService.updateBook("1", new BookEntity())).thenReturn(
            new BookEntity()
        );
        mockMvc
            .perform(
                put("/book/api/v1/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}")
            )
            .andExpect(status().isOk());
    }

    @Test
    void testDeleteBook() throws Exception {
        mockMvc
            .perform(delete("/book/api/v1/1"))
            .andExpect(status().isNoContent());
    }
}
