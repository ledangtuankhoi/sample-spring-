package com.example.book.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Order") // 404
public class BookNotFoundException extends RuntimeException {

    private final Logger logger = LoggerFactory.getLogger(
        BookNotFoundException.class
    );

    public BookNotFoundException(String id) {
        super("not found by: " + id);
        logger.error("not found by: " + id);
    }
}
