package com.example.spring_cloud_gateway_demo.Model;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String firstName;
    private String lastName;

    private String email;

    private String password;

    private String emplId;
}
