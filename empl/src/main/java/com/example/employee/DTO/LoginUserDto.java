package com.example.employee.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginUserDto {

    @Schema(example = "email@gmail.com")
    private String email;

    @Schema(example = "password")
    private String password;

    public String getEmail() {
        return email;
    }

    public LoginUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return (
            "LoginUserDto{" +
            "email='" +
            email +
            '\'' +
            ", password='" +
            password +
            '\'' +
            '}'
        );
    }
}
