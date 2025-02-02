package com.example.employee.Service;

import com.example.employee.Model.BookEntity;
import com.example.employee.Model.EmployeeEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookService {

    private final RestTemplate restTemplate;
    // private final String emplyeeServiceUrl;
    private final String bookServiceUrl;

    public BookService(
        RestTemplate restTemplate,
        // @Value("${employee.service.url}") String emplyeeServiceUrl,
        @Value("${api-gateway.book.service.url}") String bookServiceUrl
    ) {
        this.restTemplate = restTemplate;
        // this.emplyeeServiceUrl = emplyeeServiceUrl;
        this.bookServiceUrl = bookServiceUrl;
    }

    // public EmployeeEntity getEmployeeDetails(Long employeeId) {
    //     // Dùng Eureka service discovery
    //     String url =
    //         emplyeeServiceUrl + "/employee/api/v1/employees/" + employeeId;
    //     return restTemplate.getForObject(url, EmployeeEntity.class);
    // }

    // public EmployeeEntity getEmployeeDetailsWithApigetway(Long employeeId) {
    //     // Dùng Eureka service discovery
    //     String url =
    //         emplyeeServiceUrlAPIGateway + "/api/v1/employees/" + employeeId;
    //     System.out.println("url: " + url);
    //     return restTemplate.getForObject(url, EmployeeEntity.class);
    // }

    public BookEntity getBookById(String id) {
        String url = bookServiceUrl + "/api/v1/" + id;
        return restTemplate.getForObject(url, BookEntity.class);
    }
}
