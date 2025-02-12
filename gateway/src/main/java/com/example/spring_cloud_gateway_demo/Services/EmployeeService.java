package com.example.spring_cloud_gateway_demo.Services;

import com.example.spring_cloud_gateway_demo.Constants.AppContants;
import com.example.spring_cloud_gateway_demo.DTO.LoginUserDto;
import com.example.spring_cloud_gateway_demo.Model.EmployeeEntity;
import com.example.spring_cloud_gateway_demo.Responses.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(
        EmployeeService.class
    );

    private final String employeeServiceUrlAPIGateway;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public EmployeeService(
        @Value(
            "${api-gateway.employee.service.url}"
        ) String employeeServiceUrlAPIGateway,
        WebClient.Builder webClientBuilder
    ) {
        this.employeeServiceUrlAPIGateway = employeeServiceUrlAPIGateway;
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<LoginResponse> login(String username, String password) {
        // String url = "http://localhost:8080/employee/auth/login";
        String url =
            AppContants.LOADBLANCE_EMPLOYE_SERVICE + "/employee/auth/login";

        LoginUserDto loginUserDto = new LoginUserDto(username, password);
        logger.debug("LoginUserDto: {}", loginUserDto);

        return webClientBuilder
            .build()
            .post()
            .uri(url)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(loginUserDto)
            .retrieve()
            .bodyToMono(LoginResponse.class)
            .doOnNext(response -> {
                logger.debug("Sending login request to URL: {}", url);
                logger.debug("LoginUserDto: {}", loginUserDto);

                logger.debug("Login response received: {}", response);
            })
            .doOnError(error ->
                logger.error("Login request failed: {}", error.getMessage())
            )
            .onErrorResume(error -> {
                logger.error("Handling login error: {}", error.getMessage());
                return Mono.error(new RuntimeException("Unauthorized"));
            });
    }

    public Mono<LoginResponse> sigup(String username, String password) {
        // String url = "http://localhost:8080/employee/auth/login";
        String url = "lb://employee-service/employee/auth/signup";

        LoginUserDto loginUserDto = new LoginUserDto(username, password);
        logger.debug("LoginUserDto: {}", loginUserDto);

        return webClientBuilder
            .build()
            .post()
            .uri(url)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(loginUserDto)
            .retrieve()
            .bodyToMono(LoginResponse.class)
            .doOnNext(response -> {
                logger.debug("Sending login request to URL: {}", url);
                logger.debug("LoginUserDto: {}", loginUserDto);

                logger.debug("Login response received: {}", response);
            })
            .doOnError(error ->
                logger.error("Login request failed: {}", error.getMessage())
            )
            .onErrorResume(error -> {
                logger.error("Handling login error: {}", error.getMessage());
                return Mono.error(new RuntimeException("Unauthorized"));
            });
    }
}
