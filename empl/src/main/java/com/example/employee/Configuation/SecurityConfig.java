package com.example.employee.Configuation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {
//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers(
//                     "/api/v1/employees/**" // Bỏ qua xác thực cho các URL trong EmployeeController
//                 ).permitAll()
//                 .anyRequest().authenticated()
//             )
//             .csrf(csrf -> csrf.disable());
//         return http.build();
//     }
// }
