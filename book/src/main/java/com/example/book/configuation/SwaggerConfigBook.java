package com.example.book.configuation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springdoc.core.models.GroupedOpenApi;
// import org.springdoc.core.models.GroupedOpenApi;s
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigBook {

    @Bean
    public GroupedOpenApi publicGroupedOpenApi() {
        return GroupedOpenApi.builder()
            .group("public-api") // Nhóm tên hiển thị trong Swagger UI
            .pathsToMatch("/book/api/**") // Chỉ hiển thị path bắt đầu bằng "/special"
            .pathsToExclude("/bookEntities/**", "/profile/**")
            .build();
    }

    @Bean
    public GroupedOpenApi allGroupedOpenApi() {
        return GroupedOpenApi.builder()
            .group("all-api")
            .pathsToMatch("/**")
            .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("Book Swagger API") // Thay đổi tiêu đề
                    .version("v3") // Thay đổi phiên bản
                    .description(
                        "This is a custom OpenAPI documentation for our project"
                    )
                    .contact(
                        new Contact() // Thêm thông tin SwaggerConfigBookliên hệ
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
                        .url("http://localhost:8081")
                        .description("Book Server"),
                    new Server()
                        .url("http://localhost:8080")
                        .description("API gateway Server")
                )
            );
    }
}
