package com.example.employee.Configuation;

import com.example.employee.Constant.AppContants;
import com.example.employee.Service.EmployeeService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AppContants appContants;
    private static final Logger logger = LoggerFactory.getLogger(
        SecurityConfiguration.class
    );

    @Autowired
    public SecurityConfiguration(
        JwtAuthenticationFilter jwtAuthenticationFilter,
        AuthenticationProvider authenticationProvider,
        AppContants appContants
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.appContants = appContants;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(
                requests ->
                    requests
                        .requestMatchers(
                            "/employee/api/v1/auth/**",
                            appContants.getApiDocPath() + "/**",
                            "/swagger-ui/**",
                            "/swagger-resources/**"
                        )
                        .permitAll() // Cho phép truy cập không cần xác thực
                        .requestMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll() // Cho phép OPTIONS request
                        .anyRequest()
                        .authenticated() // Các request còn lại yêu cầu xác thực
            )
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Đảm bảo cấu hình CORS
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            ); // Cấu hình JWT filter
        logger.info(
            "Security Configuration: OPTIONS requests allowed without authentication."
        );

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
            Arrays.asList(
                "http://localhost:8082",
                "http://localhost:3000",
                "http://localhost:8080"
            )
        );
        configuration.setAllowedMethods(
            Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );
        configuration.setAllowedHeaders(
            Arrays.asList("Authorization", "Content-Type")
        );
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);

        // Log ra cấu hình CORS
        logger.info(
            "CORS Configuration for Employee Service: Allowed Origins - http://localhost:8082, http://localhost:3000, http://localhost:8080"
        );
        logger.info(
            "CORS Configuration for Employee Service: Allowed Methods - GET, POST, PUT, DELETE, OPTIONS"
        );

        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
