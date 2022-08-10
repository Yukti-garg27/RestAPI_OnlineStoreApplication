package com.springboot.onlineStore;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.startsWith;

/**
 * Application Level Integration Tests
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OnlineStoreApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
    private WebTestClient webTestClient;

    @Test
    public void loadApplicationContext_success() {

    }

    @Test
    public void homepage_withStaticContent_loadCorrectly() {
        webTestClient
                .get()
                .uri("/")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void defaultErrorHandler_catchesException_returnsCorrectMessage() {
        webTestClient
                .get()
                .uri("/deals_of_the_day/abc")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(String.class)
                .value(startsWith("Exception in handling request to /deals_of_the_day/abc"));
    }
}
