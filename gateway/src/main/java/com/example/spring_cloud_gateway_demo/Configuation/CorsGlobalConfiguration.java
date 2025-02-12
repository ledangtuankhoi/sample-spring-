package com.example.spring_cloud_gateway_demo.Configuation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CorsGlobalConfiguration implements WebFluxConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(
        CorsGlobalConfiguration.class
    );

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**") // Áp dụng cho tất cả các endpoint
            // .allowedOrigins("*") // Cho phép tất cả các origin
            .allowedOrigins(
                "http://localhost:8082",
                "http://localhost:3000",
                "http://localhost:8080"
            ) // Cung cấp origin cụ thể
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Các phương thức HTTP được phép
            .allowedHeaders("*") // Cho phép tất cả các headers
            .allowCredentials(true); // Cho phép gửi cookies, authorization headers, ...
        logger.info(
            "CORS Configuration for Gateway: Allowed Origins - http://localhost:8082, http://localhost:3000, http://localhost:8080"
        );
    }
}
