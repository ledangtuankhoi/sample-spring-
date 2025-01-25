package com.example.borrowing.DTO;

import com.example.borrowing.Model.BorrowingEntity.Status;
import java.time.Instant;

public class BorrowingDTO {

    private String id;
    private Instant returnBorrowing;
    private Status status;
    private String bookId;
    private String employeeId;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getReturnBorrowing() {
        return this.returnBorrowing;
    }

    public void setReturnBorrowing(Instant returnBorrowing) {
        this.returnBorrowing = returnBorrowing;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

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
