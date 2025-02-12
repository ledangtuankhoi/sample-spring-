package com.example.spring_cloud_gateway_demo.Configuation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter authenticationFilter;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
        ServerHttpSecurity http
    ) {
        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges ->
                exchanges
                    // .pathMatchers("/webjars/**", "/swagger-ui.html", "/swagger-ui/**","/v3/api-docs/**").permitAll()
                    .pathMatchers(
                        "/webjars/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/book/v3/api-docs/**",
                        "/book/api/**",
                        "/borrowing/v3/api-docs",
                        "/borrowing/api/**",
                        "/employee/v3/api-docs",
                        "/employee/api/**",
                        "/employee/auth/**",
                        "/auth/login",
                        "/auth/signup"
                    )
                    .permitAll()
                    .pathMatchers("/admin/**")
                    .hasRole("ADMIN")
                    .anyExchange()
                    .authenticated()
            )
            .authenticationManager(authenticationFilter)
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .build();
    }
}
