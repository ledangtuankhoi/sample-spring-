package com.example.spring_cloud_gateway_demo.Controller;

import com.example.spring_cloud_gateway_demo.Model.User;
import com.example.spring_cloud_gateway_demo.Responses.LoginResponse;
import com.example.spring_cloud_gateway_demo.Services.EmployeeService;
import com.netflix.discovery.converters.Auto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/me")
    public Mono<ResponseEntity<User>> me(
        @RequestHeader(
            value = "Authorization",
            required = false
        ) String authHeader
    ) {
        return employeeService
            .me(authHeader)
            .map(user -> ResponseEntity.ok(user))
            .onErrorResume(e ->
                Mono.just(ResponseEntity.status(401).body((User) null))
            );
    }
}
