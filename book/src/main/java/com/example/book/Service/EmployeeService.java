package com.example.book.Service;

import com.example.book.Model.EmployeeEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeService {

    private final RestTemplate restTemplate;
    private final String emplyeeServiceUrl;
    private final String emplyeeServiceUrlAPIGateway;

    public EmployeeService(
        RestTemplate restTemplate,
        @Value("${employee.service.url}") String emplyeeServiceUrl,
        @Value(
            "${api-gateway.employee.service.url}"
        ) String emplyeeServiceUrlAPIGateway
    ) {
        this.restTemplate = restTemplate;
        this.emplyeeServiceUrl = emplyeeServiceUrl;
        this.emplyeeServiceUrlAPIGateway = emplyeeServiceUrlAPIGateway;
    }

    public EmployeeEntity getEmployeeDetails(Long employeeId) {
        // Dùng Eureka service discovery
        String url =
            emplyeeServiceUrl + "/employee/api/v1/employees/" + employeeId;
        // return restTemplate.getForObject(url, EmployeeEntity.class);
        ResponseEntity<EmployeeEntity> responseEntity =  restTemplate.getForEntity(url, EmployeeEntity.class);
        return responseEntity.getBody();
    }

    public EmployeeEntity getEmployeeDetailsWithApigetway(Long employeeId) {
        // Dùng Eureka service discovery
        String url =
            emplyeeServiceUrlAPIGateway + "/api/v1/" + employeeId;
        System.out.println("url: " + url);
        return restTemplate.getForObject(url, EmployeeEntity.class);
    }
}
