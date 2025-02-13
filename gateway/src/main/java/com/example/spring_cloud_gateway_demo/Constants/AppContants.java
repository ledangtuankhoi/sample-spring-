package com.example.spring_cloud_gateway_demo.Constants;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppContants {

    @Value("${springdoc.api-docs.path}")
    private String apiDocPath;

    @Value("${service.url}")
    private String serviceUrl;

    // Danh sách path được phép truy cập mà không cần JWT
    public static final List<String> PUBLIC_PATHS = List.of(
        "/book/v3/api-docs/**",
        "/book/api/**",
        "/borrowing/v3/api-docs",
        "/borrowing/api/**",
        "/employee/v3/api-docs",
        "/employee/api/**",
        "/employee/auth/**",
        "/api/v1/auth/**",
        "/api/v1/users/me",
        "/webjars/**",
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    );
    public static final String LOADBLANCE_EMPLOYE_SERVICE =
        "lb://employee-service";
    public static final String LOADBLANCE_GATEWAY_SERVICE = "lb://api-gateway";

    @Autowired
    private DiscoveryClient discoveryClient;

    public String getApiGatewayUrl() {
        // Lấy danh sách các instance của ứng dụng từ Eureka
        List<ServiceInstance> instances = discoveryClient.getInstances(
            "api-gateway"
        );

        String serverUrl = "http://localhost:" + instances.get(0).getPort();
        return serverUrl;
    }

    public String getApiDocPath() {
        return apiDocPath;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }
}
