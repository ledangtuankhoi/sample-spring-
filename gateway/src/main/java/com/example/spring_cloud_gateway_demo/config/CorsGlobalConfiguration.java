package com.example.spring_cloud_gateway_demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CorsGlobalConfiguration implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Cấu hình CORS cho tất cả các request
        registry
            .addMapping("/**")
            .allowedOrigins("http://localhost:8080") // Swagger UI đang chạy ở đây
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Các phương thức HTTP được phép (bao gồm OPTIONS)
            .allowedHeaders("*") // Cho phép tất cả các header trong yêu cầu
            .allowCredentials(true); // Cho phép gửi cookies trong yêu cầu
    }
    // how to config and test cors for this service
}
