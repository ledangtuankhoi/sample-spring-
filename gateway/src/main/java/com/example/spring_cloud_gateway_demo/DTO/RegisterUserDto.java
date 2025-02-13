package com.example.spring_cloud_gateway_demo.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterUserDto {
 
    @Schema(example = "email@gmail.com")
    private String email;

    @Schema(example = "password")
    private String password;

    @Schema(example = "firstName")
    private String firstName;

    @Schema(example = "lastName")
    private String lastName;

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
