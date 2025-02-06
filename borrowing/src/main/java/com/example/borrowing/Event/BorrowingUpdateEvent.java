package com.example.borrowing.Event;

import com.example.borrowing.Model.BorrowingEntity.Status;

public class BorrowingUpdateEvent {

    private String bookId;
    private String employeeId;
    private Status status;

    public BorrowingUpdateEvent(
        String bookId,
        String employeeId,
        Status status
    ) {
        this.bookId = bookId;
        this.employeeId = employeeId;
        this.status = status;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return (
            "{" +
            " bookId='" +
            getBookId() +
            "'" +
            ", employeeId='" +
            getEmployeeId() +
            "'" +
            ", status='" +
            getStatus() +
            "'" +
            "}"
        );
    }
}
