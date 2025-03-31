package com.example.book.configuation;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

class AppConfigTest {

    private final AppConfig appConfig = new AppConfig();

    @Test
    void testRestTemplate() {
        RestTemplate restTemplate = appConfig.restTemplate();
        assertNotNull(restTemplate);
    }
}
