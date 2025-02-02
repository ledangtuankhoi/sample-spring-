package com.example.employee.Service;

import com.example.employee.Model.BookEntity;
import com.example.employee.Model.BorrowingEntity;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BorrowingService {

    private final RestTemplate restTemplate;
    // private final String emplyeeServiceUrl;
    private final String serviceUrl;
    private final BookService bookService;

    public BorrowingService(
        RestTemplate restTemplate,
        // @Value("${employee.service.url}") String emplyeeServiceUrl,
        @Value("${api-gateway.borrowing.service.url}") String ServiceUrl,
        BookService bookService
    ) {
        this.restTemplate = restTemplate;
        // this.emplyeeServiceUrl = emplyeeServiceUrl;
        this.serviceUrl = ServiceUrl;
        this.bookService = bookService;
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

    public List<BorrowingEntity> getBorrowingByEmplId(String id) {
        // http://localhost:8080/borrowing/api/v1/employee/{employeeId}?employeeId=
        String url = serviceUrl + "api/v1/employee/" + id;
        // return restTemplate.getForEntity(url, BorrowingEntity[].class);

        System.out.println(
            "Working Directory = " + System.getProperty("user.dir")
        );
        System.out.println("url: " + url);

        ResponseEntity<BorrowingEntity[]> response = restTemplate.getForEntity(
            url,
            BorrowingEntity[].class
        );

        System.out.println(response);
        BorrowingEntity[] listEntities = response.getBody();
        return Arrays.asList(listEntities);
    }
}
