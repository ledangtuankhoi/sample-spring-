package com.example.borrowing.Service;

import com.example.borrowing.Model.BookEntity;
import com.example.borrowing.Model.EmployeeEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookService {

    private final RestTemplate restTemplate;
    private final String bookServiceUrl;

    public BookService(
        RestTemplate restTemplate,
        @Value("${api-gateway.book.service.url}") String bookServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.bookServiceUrl = bookServiceUrl;
    }

    public BookEntity getBookById(String id) {
        String url = bookServiceUrl + "/api/v1/" + id;
        return restTemplate.getForObject(url, BookEntity.class);
    }
}
