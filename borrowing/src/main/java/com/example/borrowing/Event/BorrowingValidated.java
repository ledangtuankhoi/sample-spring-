package com.example.borrowing.Event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingValidated extends BorrowingRequest {

    boolean isBookValid;
    boolean isEmplValid;

    public BorrowingValidated(
        BorrowingRequest request,
        boolean isBookValid,
        boolean isEmplValid
    ) {
        super(
            request.getRequestId(),
            request.getBookId(),
            request.getEmployeeId()
        );
        this.isBookValid = isBookValid;
        this.isEmplValid = isEmplValid;
    }

    public BorrowingValidated(
        String requestId,
        String bookId,
        String emplId,
        boolean isBookValid,
        boolean isEmplValid
    ) {
        super(requestId, bookId, emplId);
        this.isBookValid = isBookValid;
        this.isEmplValid = isEmplValid;
    }
}
