package com.example.employee.Event;

import com.example.employee.Model.BorrowingEntity.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingEvent {

    private Status status;
    private String bookId;
    private String employeeId;
    private String title;
    private String action;

    // Chuyển đổi đối tượng thành JSON
    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                "Failed to convert BorrowingEvent to JSON",
                e
            );
        }
    }

    public BorrowingEvent fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, BorrowingEvent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                "Failed to convert BorrowingEvent to JSON",
                e
            );
        }
    }
}
