package com.example.borrowing.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Order") // 404
public class BorrowingNotFoundException extends RuntimeException {

    public BorrowingNotFoundException(String id) {
        super("not found by: " + id);
        System.err.println("not found by: " + id);
    }
}
