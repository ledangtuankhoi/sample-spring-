package com.example.borrowing.Configuation;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            // .allowedOrigins("http://api-gateway") // Cho phép Swagger UI (localhost:8080) gọi tới
            .allowedOrigins("http://localhost:8080") // Cho phép Swagger UI (localhost:8080) gọi tới
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*")
            .allowCredentials(true); // Cho phép cookie trong yêu cầu
        // ;
    }
}
