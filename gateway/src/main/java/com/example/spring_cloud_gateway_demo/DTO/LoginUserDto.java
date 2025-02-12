package com.example.spring_cloud_gateway_demo.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
public class LoginUserDto {

    @Schema(example = "email@gmail.com")
    private String email;

    @Schema(example = "password")
    private String password;
}
