package com.example.borrowing.Event;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingRequest {

    @NotNull
    String requestId;

    @NotNull
    String bookId;

    @NotNull
    String employeeId;
}
