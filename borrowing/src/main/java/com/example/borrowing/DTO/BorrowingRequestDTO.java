package com.example.borrowing.DTO;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Borrowing Request DTO")
public class BorrowingRequestDTO {

    @Parameter(description = "book id")
    private String bookId;

    @Parameter(description = "empl id")
    private String employeeId;

    public BorrowingRequestDTO(String bookId, String employeeId) {
        this.bookId = bookId;
        this.employeeId = employeeId;

        System.err.println("book" + bookId + " empl" + employeeId);
    }

    public BorrowingRequestDTO() {}

    public String getBookId() {
        return this.bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
