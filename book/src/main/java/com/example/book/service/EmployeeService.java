package com.example.book.service;

import com.example.book.model.EmployeeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeService {

    private final RestTemplate restTemplate;
    private final String emplyeeServiceUrl;
    private final String emplyeeServiceUrlAPIGateway;
    private Logger logger = LoggerFactory.getLogger(getClass());

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
        String url = emplyeeServiceUrl + "/employee/api/v1/" + employeeId;
        ResponseEntity<EmployeeEntity> responseEntity =
            restTemplate.getForEntity(url, EmployeeEntity.class);
        return responseEntity.getBody();
    }

    public EmployeeEntity getEmployeeDetailsWithApigetway(Long employeeId) {
        // Dùng Eureka service discovery
        String url = emplyeeServiceUrlAPIGateway + "api/v1/" + employeeId;
        logger.info("url: " + url);
        return restTemplate.getForObject(url, EmployeeEntity.class);
    }
}
