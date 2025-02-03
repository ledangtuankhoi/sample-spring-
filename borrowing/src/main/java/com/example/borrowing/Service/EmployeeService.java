package com.example.borrowing.Service;

import com.example.borrowing.Model.BookEntity;
import com.example.borrowing.Model.EmployeeEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeService {

    private final RestTemplate restTemplate;
    private final String emplServiceUrl;

    public EmployeeService(
        RestTemplate restTemplate,
        @Value("${api-gateway.employee.service.url}") String emplServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.emplServiceUrl = emplServiceUrl;
    }

    public BookEntity getById(String id) {
        String url = emplServiceUrl + "/api/v1/" + id;
        return restTemplate.getForObject(url, BookEntity.class);
    }
}
