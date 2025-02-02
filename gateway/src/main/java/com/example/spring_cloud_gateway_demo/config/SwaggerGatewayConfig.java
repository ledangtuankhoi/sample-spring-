package com.example.spring_cloud_gateway_demo.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class SwaggerGatewayConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
            .group("public-api") // Nhóm tên hiển thị trong Swagger UI
            .pathsToMatch(
                "/book/api/**",
                "/employee/api/**",
                "/borrowing/api/**"
            ) // Chỉ hiển thị path bắt đầu bằng "/special"
            .pathsToExclude(
                "/bookEntities/**",
                "/employeeEntities/**",
                "/borrowingEntities/**",
                "/profile/**"
            )
            .build();
    }
}
