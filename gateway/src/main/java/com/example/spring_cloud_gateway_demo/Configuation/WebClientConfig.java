package com.example.spring_cloud_gateway_demo.Configuation;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder
            .routes()
            .route("book-service-route", r ->
                r
                    .path("/book/**")
                    // .filters(f -> f.filter(jwtAuthFilter))
                    .uri("lb://book-service")
            )
            .route("employee-service-route", r ->
                r
                    .path("/employee/**")
                    // .filters(f -> f.filter(jwtAuthFilter))
                    .uri("lb://employee-service")
            )
            .route("borrowing-service-route", r ->
                r
                    .path("/borrowing/**")
                    // .filters(f -> f.filter(jwtAuthFilter))
                    .uri("lb://borrowing-service")
            )
            .build();
    }
}
