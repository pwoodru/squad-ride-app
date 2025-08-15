package com.squadride.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "spring.sql.init.mode=never",
    "spring.jpa.defer-datasource-initialization=true"
})
class SquadRideApplicationTests {

    @Test
    void contextLoads() {
        // Test that the Spring context loads successfully
    }
}