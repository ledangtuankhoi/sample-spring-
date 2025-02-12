package com.example.spring_cloud_gateway_demo.Configuation;

import com.example.spring_cloud_gateway_demo.Constants.AppContants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigEmployee {

    private final DiscoveryClient discoveryClient;
    private final AppContants appContants;

    public SwaggerConfigEmployee(
        DiscoveryClient discoveryClient,
        AppContants appContants
    ) {
        this.discoveryClient = discoveryClient;
        this.appContants = appContants;
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("bearer");
    }

    @Bean
    public OpenAPI customOpenAPI() {
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
                    new Server()
                        .url(appContants.getServiceUrl())
                        .description("Local Server")
                )
            )
            .addSecurityItem(
                new SecurityRequirement().addList("Bearer Authentication")
            )
            .components(
                new Components()
                    .addSecuritySchemes(
                        "Bearer Authentication",
                        createAPIKeyScheme()
                    )
            );
    }
}
