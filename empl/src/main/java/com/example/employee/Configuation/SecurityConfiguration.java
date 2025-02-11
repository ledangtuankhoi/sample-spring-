package com.example.employee.Configuation;

import com.example.employee.Constant.AppContants;
import com.example.employee.Service.EmployeeService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    private final EmployeeService employeeService;
    private final DiscoveryClient discoveryClient;
    private AppContants appContants;
    private static final Logger logger = LoggerFactory.getLogger(
        SecurityConfiguration.class
    );

    @Autowired
    public SecurityConfiguration(
        JwtAuthenticationFilter jwtAuthenticationFilter,
        DiscoveryClient discoveryClient,
        AuthenticationProvider authenticationProvider,
        EmployeeService employeeService,
        AppContants appContants
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.discoveryClient = discoveryClient;
        this.employeeService = employeeService;
        this.appContants = appContants;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(requests ->
                requests
                    .requestMatchers(
                        "/employee/auth/**",
                        appContants.getApiDocPath() + "/**",
                        "/swagger-ui/**",
                        "/swagger-resources/**"
                    )
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            )
            .sessionManagement(management ->
                management.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
            List.of("http://localhost:8080", appContants.getApiGatewayUrl())
        );
        configuration.setAllowedMethods(List.of("GET", "POST"));
        configuration.setAllowedHeaders(
            List.of("Authorization", "Content-Type")
        );

        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        logger.info("CorsConfigurationSource: ");
        logger.info(configuration.getAllowedOrigins().toString()); 
        logger.info(configuration.getAllowedHeaders().toString()); 
        logger.info(configuration.getAllowedMethods().toString());  

        return source;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web ->
            web
                .ignoring()
                .requestMatchers(
                    appContants.getApiDocPath() + "/**",
                    "/swagger-ui/**",
                    "/swagger-resources/**"
                );
    }
}
