package com.example.book.exception;

import com.example.book.dtos.ErrorDetails;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalControllerException extends ResponseEntityExceptionHandler {

    // handles exceptions of the type BookNotFoundException and returns a ResponseEntity to the controller
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorDetails> exceptionBook(
        BookNotFoundException exception,
        WebRequest webRequest
    ) {
        ErrorDetails errorDetails = new ErrorDetails(
            new Date(),
            exception.getMessage(),
            webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            "An error occurred: " + ex.getMessage()
        );
    }

    // Handles exceptions for request parameter that are incorrectly formated or invalid, and return a list parameter along with corresponding error message
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {
        Map<String, String> errorDetails = new HashMap<>();
        ex
            .getBindingResult()
            .getAllErrors()
            .forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errorDetails.put(fieldName, message);
            });
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
