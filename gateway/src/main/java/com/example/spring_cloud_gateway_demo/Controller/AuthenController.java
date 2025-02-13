package com.example.spring_cloud_gateway_demo.Controller;

import com.example.spring_cloud_gateway_demo.Configuation.JWTUtil;
import com.example.spring_cloud_gateway_demo.DTO.LoginUserDto;
import com.example.spring_cloud_gateway_demo.DTO.RegisterUserDto;
import com.example.spring_cloud_gateway_demo.Model.User;
import com.example.spring_cloud_gateway_demo.Responses.LoginResponse;
import com.example.spring_cloud_gateway_demo.Services.EmployeeService;
import org.mapstruct.control.MappingControl.Use;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public Mono<ResponseEntity<LoginResponse>> login(
        @RequestBody LoginUserDto loginRequest
    ) {
        return employeeService
            .login(loginRequest.getEmail(), loginRequest.getPassword())
            .map(token -> ResponseEntity.ok(token))
            .onErrorResume(e ->
                Mono.just(ResponseEntity.status(401).body((LoginResponse) null))
            );
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<User>> register(
        @RequestBody RegisterUserDto registerUserDto
    ) {
        return employeeService
            .resgister(registerUserDto)
            .map(token -> ResponseEntity.ok(token))
            .onErrorResume(e ->
                Mono.just(ResponseEntity.status(401).body((User) null))
            );
    }
}
