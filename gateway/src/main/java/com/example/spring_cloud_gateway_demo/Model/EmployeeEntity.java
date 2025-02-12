package com.example.spring_cloud_gateway_demo.Model;

import java.time.Instant;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class EmployeeEntity {

    private String id;
    private String firstName;
    private String lastName;
    private String KIN;
    private Boolean isDiscipilined;
    private Instant createdAt;
    private Instant updatedAt;
}
