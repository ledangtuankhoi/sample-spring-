package com.example.borrowing.Exception;

import org.springframework.http.HttpStatus;

public class BorrowingNotFoundException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public BorrowingNotFoundException(String id) {}

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
