package com.example.book.configuation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.springdoc.core.models.GroupedOpenApi;

class SwaggerConfigBookTest {

    private final SwaggerConfigBook swaggerConfigBook = new SwaggerConfigBook();

    @Test
    void testPublicGroupedOpenApi() {
        GroupedOpenApi groupedOpenApi =
            swaggerConfigBook.publicGroupedOpenApi();
        assertNotNull(groupedOpenApi);
        assertEquals("public-api", groupedOpenApi.getGroup());
    }

    @Test
    void testAllGroupedOpenApi() {
        GroupedOpenApi groupedOpenApi = swaggerConfigBook.allGroupedOpenApi();
        assertNotNull(groupedOpenApi);
        assertEquals("all-api", groupedOpenApi.getGroup());
    }

    @Test
    void testCustomOpenAPI() {
        OpenAPI openAPI = swaggerConfigBook.customOpenAPI();
        assertNotNull(openAPI);
        assertEquals("Book Swagger API", openAPI.getInfo().getTitle());
        assertEquals("v3", openAPI.getInfo().getVersion());
        assertEquals(
            "This is a custom OpenAPI documentation for our project",
            openAPI.getInfo().getDescription()
        );
        assertEquals("John Doe", openAPI.getInfo().getContact().getName());
        assertEquals(
            "john.doe@example.com",
            openAPI.getInfo().getContact().getEmail()
        );
        assertEquals(
            "https://example.com",
            openAPI.getInfo().getContact().getUrl()
        );
        assertEquals("Apache 2.0", openAPI.getInfo().getLicense().getName());
        assertEquals(
            "https://www.apache.org/licenses/LICENSE-2.0.html",
            openAPI.getInfo().getLicense().getUrl()
        );
        assertEquals(2, openAPI.getServers().size());
        assertEquals(
            "http://localhost:8081",
            openAPI.getServers().get(0).getUrl()
        );
        assertEquals(
            "Book Server",
            openAPI.getServers().get(0).getDescription()
        );
        assertEquals(
            "http://localhost:8080",
            openAPI.getServers().get(1).getUrl()
        );
        assertEquals(
            "API gateway Server",
            openAPI.getServers().get(1).getDescription()
        );
    }
}
