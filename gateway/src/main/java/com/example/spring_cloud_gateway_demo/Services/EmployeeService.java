package com.example.spring_cloud_gateway_demo.Services;

import com.example.spring_cloud_gateway_demo.Constants.AppContants;
import com.example.spring_cloud_gateway_demo.DTO.LoginUserDto;
import com.example.spring_cloud_gateway_demo.DTO.RegisterUserDto;
import com.example.spring_cloud_gateway_demo.Model.User;
import com.example.spring_cloud_gateway_demo.Responses.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
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
            AppContants.LOADBLANCE_EMPLOYE_SERVICE +
            "/employee/api/v1/auth/login";

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

    public Mono<User> resgister(RegisterUserDto register) {
        // String url = "http://localhost:8080/employee/auth/login";
        String url =
            AppContants.LOADBLANCE_EMPLOYE_SERVICE +
            "/employee/api/v1/auth/signup";

        RegisterUserDto registerUserDto = new RegisterUserDto(
            register.getEmail(),
            register.getPassword(),
            register.getFirstName(),
            register.getLastName()
        );

        return webClientBuilder
            .build()
            .post()
            .uri(url)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(registerUserDto)
            .retrieve()
            .bodyToMono(User.class)
            .doOnNext(response -> {
                logger.debug("Sending login request to URL: {}", url);
                logger.debug("LoginUserDto: {}", registerUserDto);

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

    public Mono<User> me(String authHeader) {
        String url =
            AppContants.LOADBLANCE_EMPLOYE_SERVICE +
            "/employee/api/v1/users/me";

        return Mono.deferContextual(ctx -> {
            String token = authHeader.replace("Bearer ", "");
            if (token == null) {
                logger.warn("Not found the Authorization Header!");
            } else {
                logger.debug("Authorization Header: {}", token);
            }
            return webClientBuilder
                .build()
                .get()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token) // Thêm Authorization Header
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    logger.debug(" [REQUEST] GET {}", url);
                    logger.debug(
                        " [REQUEST] Headers: {}",
                        response.headers().asHttpHeaders()
                    );

                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(User.class);
                    } else {
                        return response
                            .createException()
                            .flatMap(error -> {
                                logger.error(
                                    " [RESPONSE] HTTP {} - {}",
                                    response.statusCode(),
                                    error.getMessage()
                                );
                                return Mono.error(
                                    new RuntimeException("Unauthorized")
                                );
                            });
                    }
                })
                .doOnSubscribe(sub ->
                    logger.debug(" [SUBSCRIBE] Sending request to {}", url)
                )
                .doOnSuccess(user ->
                    logger.debug("[RESPONSE] Received user: {}", user)
                )
                .doOnError(error ->
                    logger.error(
                        " [ERROR] Request failed: {}",
                        error.getMessage()
                    )
                );
        });
    }
}
