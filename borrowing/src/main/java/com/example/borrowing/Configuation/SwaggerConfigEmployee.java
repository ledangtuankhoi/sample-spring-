package com.example.borrowing.Configuation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class SwaggerConfigEmployee {

    private final DiscoveryClient discoveryClient;

    public SwaggerConfigEmployee(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        // Lấy danh sách các instance của ứng dụng từ Eureka
        List<ServiceInstance> instances = discoveryClient.getInstances(
            "borrowing-service"
        ); // Thay "app-name" bằng tên ứng dụng của bạn

        System.out.println("Instances: " + instances);
        // Lấy URL từ instance đầu tiên (nếu có)
        String serverUrl = instances.isEmpty()
            ? "http://localhost:8083" // Giá trị mặc định nếu không tìm thấy
            : instances.get(0).getUri().toString();

        return new OpenAPI()
            .info(
                new Info()
                    //   .title("Custom API Documentation") // Thay đổi tiêu đề
                    .version("v1") // Thay đổi phiên bản
                    .description(
                        "This is a custom OpenAPI documentation for our project"
                    )
                    .contact(
                        new Contact() // Thêm thông tin liên hệ
                            .name("John Doe")
                            .email("john.doe@example.com")
                            .url("https://example.com")
                    )
                    .license(
                        new License() // Thêm thông tin license
                            .name("Apache 2.0")
                            .url(
                                "https://www.apache.org/licenses/LICENSE-2.0.html"
                            )
                    )
            )
            .servers(
                List.of(
                    new Server().url(serverUrl).description("Local Server"),
                    new Server()
                        .url("http://localhost:8080")
                        .description("API Gateway")
                )
            );
    }
}
