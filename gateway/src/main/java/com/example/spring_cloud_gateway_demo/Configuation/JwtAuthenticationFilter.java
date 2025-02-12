package com.example.spring_cloud_gateway_demo.Configuation;

import com.example.spring_cloud_gateway_demo.Services.EmployeeService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements ReactiveAuthenticationManager {

    private final JWTUtil jwtUtil;
    private final EmployeeService emplService;

    public JwtAuthenticationFilter(
        JWTUtil jwtUtil,
        EmployeeService emplService
    ) {
        this.jwtUtil = jwtUtil;
        this.emplService = emplService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication)
        throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        String username = jwtUtil.extractUsername(token);
        return Mono.just(authentication);
    }

    public ServerAuthenticationConverter authenticationConverter() {
        return new ServerAuthenticationConverter() {
            @Override
            public Mono<Authentication> convert(ServerWebExchange exchange) {
                String token = exchange
                    .getRequest()
                    .getHeaders()
                    .getFirst("Authorization");
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                    return Mono.just(
                        SecurityContextHolder.getContext().getAuthentication()
                    );
                }
                return Mono.empty();
            }
        };
    }
}
