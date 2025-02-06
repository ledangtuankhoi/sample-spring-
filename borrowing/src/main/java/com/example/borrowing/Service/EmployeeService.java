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

    public EmployeeEntity getById(String id) {
        // String url = emplServiceUrl + "/api/v1/" + id;
        // return restTemplate.getForObject(url, BookEntity.class);

        String url = emplServiceUrl + "/api/v1/" + id;
        try {
            // return restTemplate.getForObject(url, BookEntity.class);
            ResponseEntity<EmployeeEntity> responseEntity =
                restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    EmployeeEntity.class
                );
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            // Lấy mã lỗi HTTP
            String statusCode = e.getStatusCode().toString();

            // Lấy message từ response body
            String responseBody = e.getResponseBodyAsString();

            // Log hoặc xử lý lỗi
            System.err.println("HTTP Status Code: " + statusCode);
            System.err.println("Response Body: " + responseBody);

            // Ném lỗi hoặc trả về thông tin lỗi
            throw new RuntimeException("Error fetching book: " + responseBody);
        } catch (RestClientException e) {
            throw new BookServiceException(
                "Error occurred while fetching book with id: " + id,
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage()
            );
        }
    }
}
