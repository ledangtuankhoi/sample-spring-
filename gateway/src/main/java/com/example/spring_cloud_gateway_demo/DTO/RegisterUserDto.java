package com.example.spring_cloud_gateway_demo.DTO;

public class RegisterUserDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public String getEmail() {
        return email;
    }

    public RegisterUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return (
            "{" +
            " email='" +
            getEmail() +
            "'" +
            ", password='" +
            getPassword() +
            "'" +
            ", firstName='" +
            getFirstName() +
            "'" +
            ", lastName='" +
            getLastName() +
            "'" +
            "}"
        );
    }
}
