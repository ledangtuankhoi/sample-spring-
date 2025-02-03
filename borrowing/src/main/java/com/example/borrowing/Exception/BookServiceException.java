package com.example.borrowing.Exception;

import org.springframework.http.HttpStatus;

public class BookServiceException extends RuntimeException {

    private HttpStatus status;

    private String responseBody;

    public BookServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public BookServiceException(
        String message,
        HttpStatus status,
        String responseBody
    ) {
        super(message);
        this.status = status;

        this.responseBody = responseBody;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
