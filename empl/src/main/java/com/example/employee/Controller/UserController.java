package com.example.employee.Controller;

import com.example.employee.Model.User;
import com.example.employee.Service.UserService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/employee/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser(
        @RequestHeader(
            value = "Authorization",
            required = false
        ) String authHeader
    ) {
        if (authHeader == null) {
            System.out.println(
                "[Employee Service] No Authorization Header received"
            );
        }

        System.out.println(
            "🔹 [Employee Service] Received Authorization Header: " + authHeader
        );
        Authentication authentication = SecurityContextHolder.getContext()
            .getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }
}
