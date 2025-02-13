package com.example.spring_cloud_gateway_demo.Filters;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

import java.net.URI;
import java.util.Collections;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter {

    Log log = LogFactory.getLog(getClass());

    @Override
    public Mono<Void> filter(
        ServerWebExchange exchange,
        GatewayFilterChain chain
    ) {
        Set<URI> uris = exchange.getAttributeOrDefault(
            GATEWAY_ORIGINAL_REQUEST_URL_ATTR,
            Collections.emptySet()
        );
        String originalUri = (uris.isEmpty())
            ? "Unknown"
            : uris.iterator().next().toString();
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        URI routeUri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
        log.debug(
            "Incoming request " +
            originalUri +
            " is routed to id: " +
            route.getId() +
            ", uri:" +
            routeUri
        );

        ServerHttpRequest request = exchange.getRequest();

        if (request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            String authHeader = request
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);
            System.out.println(
                "🔹 [API Gateway] Forwarding Authorization Header: " +
                authHeader
            );
        } else {
            System.out.println("[API Gateway] Missing Authorization Header");
        }

        return chain.filter(exchange);
    }
}
