package com.example.employee.Controller;

import com.example.employee.DTO.LoginUserDto;
import com.example.employee.DTO.RegisterUserDto;
import com.example.employee.Model.User;
import com.example.employee.Responses.LoginResponse;
import com.example.employee.Service.AuthenticationService;
import com.example.employee.Service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/employee/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(
        JwtService jwtService,
        AuthenticationService authenticationService
    ) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(
        @RequestBody RegisterUserDto registerUserDto
    ) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(
        @RequestBody LoginUserDto loginUserDto
    ) {
        User authenticatedUser = authenticationService.authenticate(
            loginUserDto
        );

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse()
            .setToken(jwtToken)
            .setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
