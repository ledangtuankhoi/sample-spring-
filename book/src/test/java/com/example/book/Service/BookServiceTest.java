package com.example.book.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.book.Model.BookEntity;
import com.example.book.Service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    void testFakeBook() {
        // BookEntity bookEntity = new BookEntity();
        // bookEntity.setName("fake");
        // bookEntity.setAuthor("fake");
        // bookEntity.setIsReady(true);

        // bookService.fakeBook(bookEntity);
        // assertNotNull(bookEntity);
        // assertEquals("fake - update", bookEntity.getName());
    }
}
