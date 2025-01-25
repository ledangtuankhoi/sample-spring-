package com.example.spring_cloud_gateway_demo.config;
 
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.cloud.gateway.filter.GlobalFilter;
// import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;
// import org.springframework.web.server.ServerWebExchange;
// import reactor.core.publisher.Mono;

// @Slf4j
// @Component
// @Order(-1) // Đảm bảo filter chạy đầu tiên
// public class LoggingFilter implements GlobalFilter {

//     @Override
//     public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//         String requestPath = exchange.getRequest().getURI().toString();
//         log.info("Request Path: {}", requestPath);
//         return chain.filter(exchange).doOnSuccess(aVoid -> {
//             log.info("Response Status: {}", exchange.getResponse().getStatusCode());
//         });
//     }
// }