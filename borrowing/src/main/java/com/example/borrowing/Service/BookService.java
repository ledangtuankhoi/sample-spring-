package com.example.borrowing.Service;

import com.example.borrowing.Exception.BookServiceException;
import com.example.borrowing.Model.BookEntity;
import com.example.borrowing.Model.EmployeeEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
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
        try {
            // return restTemplate.getForObject(url, BookEntity.class);
            ResponseEntity<BookEntity> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                BookEntity.class
            );
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            throw new BookServiceException(
                "Book not found with id: " + id,
                HttpStatus.valueOf(e.getStatusCode().value()),
                e.getResponseBodyAsString()
            );
        } catch (RestClientException e) {
            throw new BookServiceException(
                "Error occurred while fetching book with id: " + id,
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage()
            );
        }
    }
}
