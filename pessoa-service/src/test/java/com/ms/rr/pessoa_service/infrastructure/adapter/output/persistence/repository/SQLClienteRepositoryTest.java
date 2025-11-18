package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.repository;

import com.ms.rr.pessoa_service.api.output.ClienteOutputPortTest;
import com.ms.rr.pessoa_service.domain.port.output.ClienteOutputPort;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan
@DataJpaTest
class SQLClienteRepositoryTest extends ClienteOutputPortTest {

    @Autowired
    ClienteOutputPort clienteRepository;

    @Autowired
    EntityManager entityManager;

    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>( "postgres:16.6")
            .withDatabaseName("pessoa_service_test")
            .withUsername("rodtest")
            .withPassword("passwordtest");

    @BeforeAll
    void setUp() {
        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
        System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
        System.setProperty("spring.datasource.driver-class-name", postgreSQLContainer.getDriverClassName());
        postgreSQLContainer.start();
    }

    @Override
    public ClienteOutputPort getClienteOutputPort() {
        return clienteRepository;
    }

    @Transactional
    @AfterEach
    void tearDown() {
        entityManager.createNativeQuery("TRUNCATE TABLE tb_pessoa").executeUpdate();
    }
}