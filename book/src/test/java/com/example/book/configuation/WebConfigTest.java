package com.example.book.configuation;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

class WebConfigTest {

    private final WebConfig webConfig = new WebConfig();

    @Test
    void testAddCorsMappings() {
        CorsRegistry registry = new CorsRegistry();
        webConfig.addCorsMappings(registry);
        assertNotNull(registry);
    }
}
