package com.ms.rr.produto_service.integration;

import org.junit.jupiter.api.AfterAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.wiremock.integrations.testcontainers.WireMockContainer;

@Testcontainers
@AutoConfigureWebTestClient(timeout = "3600000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

    @Autowired
    protected WebTestClient webTestClient;

    @Container
    protected final static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.12")
            .withReuse(true);

    @Container
    protected static final WireMockContainer wiremock = new WireMockContainer("wiremock/wiremock:3.4.2")
            .withExposedPorts(8080);

    @DynamicPropertySource
    protected static void dynamicPropertiesProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);

        String wireMockBaseUrl = "http://" + wiremock.getHost() + ":" + wiremock.getMappedPort(8080);
        registry.add("integracao.pessoas-service.url", () -> wireMockBaseUrl);
    }
}
