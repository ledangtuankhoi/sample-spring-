package com.example.spring_cloud_gateway_demo.Filters;

import com.example.spring_cloud_gateway_demo.Configuation.JWTUtil;
import com.example.spring_cloud_gateway_demo.Constants.AppContants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtWebFilter implements WebFilter {

    @Autowired
    private JWTUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(
        JwtWebFilter.class
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // ⏩ Bỏ qua filter nếu path thuộc danh sách `permitAll()`
        String path = exchange.getRequest().getURI().getPath();
        if (isPublicPath(path)) {
            logger.info("✅ Bỏ qua xác thực JWT cho path: " + path);
            return chain.filter(exchange);
        }
        // Kiểm tra Authorization Header
        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return onError(
                exchange,
                "Missing Authorization Header",
                HttpStatus.UNAUTHORIZED
            );
        }

        String authHeader = request
            .getHeaders()
            .getFirst(HttpHeaders.AUTHORIZATION);
        String token = authHeader.replace("Bearer ", "");

        // Kiểm tra JWT Token
        if (!jwtUtil.validateToken(token)) {
            return onError(
                exchange,
                "Invalid JWT Token",
                HttpStatus.UNAUTHORIZED
            );
        }

        // ✅ Nếu hợp lệ, thêm lại token vào request để forward
        ServerHttpRequest modifiedRequest = exchange
            .getRequest()
            .mutate()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private boolean isPublicPath(String path) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return AppContants.PUBLIC_PATHS.stream()
            .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private Mono<Void> onError(
        ServerWebExchange exchange,
        String err,
        HttpStatus httpStatus
    ) {
        exchange.getResponse().setStatusCode(httpStatus);
        return exchange.getResponse().setComplete();
    }
}
