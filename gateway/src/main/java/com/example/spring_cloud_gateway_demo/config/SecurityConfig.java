package com.example.spring_cloud_gateway_demo.config;
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

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
        // .cors(cors -> cors.configurationSource( corsWebFilter())) // CORS setup
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // .pathMatchers("/webjars/**", "/swagger-ui.html", "/swagger-ui/**","/v3/api-docs/**").permitAll()
                        .pathMatchers("/webjars/**", 
                        "/swagger-ui.html", 
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/book/v3/api-docs/**",
                        "/book/api/**",
                        "/borrowing/v3/api-docs",
                        "/borrowing/api/**",
                        "/employee/v3/api-docs",
                        "/employee/api/**").permitAll()

                        .pathMatchers("/admin/**").hasRole("ADMIN")
                        .anyExchange().authenticated()
                )
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .build();
    }

    // @Bean
    // public CorsWebFilter corsWebFilter() {
    //     CorsConfiguration corsConfig = new CorsConfiguration();
    //     corsConfig.addAllowedOrigin("*"); // Cho phép mọi nguồn gốc
    //     corsConfig.addAllowedMethod("*"); // Cho phép mọi phươSng thức HTTP
    //     corsConfig.addAllowedHeader("*"); // Cho phép mọi header
    //     corsConfig.setAllowCredentials(true);

    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", corsConfig);

    //     return new CorsWebFilter(source);
    // }

}
