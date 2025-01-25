package com.example.employee.Exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(String id) {
        super("Could not find emp " + id);
    }
}
