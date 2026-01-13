package com.voyageconnect;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Smoke test to verify that the application context loads
 * with the local profile and basic endpoints are available.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
class VoyageConnectApplicationSmokeIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void homePageIsAccessible() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Bienvenue sur VoyageConnect");
    }

    @Test
    void apiVoyagesReturnsSeedData() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/voyages", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Check for one of the seeded voyages
        assertThat(response.getBody()).contains("Semaine Romantique à Paris");
    }
}
