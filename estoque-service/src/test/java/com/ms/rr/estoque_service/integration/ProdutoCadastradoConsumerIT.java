package com.ms.rr.estoque_service.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.rr.estoque_service.EstoqueServiceApplication;
import com.ms.rr.estoque_service.adapter.input.consumer.ProdutoCadastradoConsumer;
import com.ms.rr.estoque_service.domain.dto.in.CreateEstoque;
import com.ms.rr.estoque_service.domain.model.ProdutoCriadoEvent;
import com.ms.rr.estoque_service.factory.ProdutoCriadoEventFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@Import(KafkaTestConfiguration.class)
@SpringBootTest(classes = EstoqueServiceApplication.class)
@DirtiesContext
@Testcontainers
@ActiveProfiles("test")
public class ProdutoCadastradoConsumerIT {

    @Container
    protected final static KafkaContainer kafkaContainer = new KafkaContainer("apache/kafka-native:latest");


    @DynamicPropertySource
    protected static void dynamicPropertiesProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

//    @Autowired
//    private EstoqueUseCase estoqueUseCase;

    @Autowired
    private ProdutoCadastradoConsumer consumer;

    private ProdutoCriadoEvent produtoCriadoEvent;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${topicos.produto-cadastrado.cosumer.topic}")
    private String topic;

    @Captor
    private ArgumentCaptor<CreateEstoque> captor;


    @BeforeEach
    public void setUp() {
        produtoCriadoEvent = ProdutoCriadoEventFactory.buildProdutoCriadoEvent();
        consumer.resetLatch();
    }

    @Nested
    class ListenProdutoCriadoEvent {

        @Test
        void shouldListenProdutoCriadoEventSuccesfully() throws Exception {
            // Arrange
            String message = objectMapper.writeValueAsString(produtoCriadoEvent);

//            doNothing().when(estoqueUseCase).salvar(captor.capture());

            // Act
            kafkaTemplate.send(topic, produtoCriadoEvent.getProdutoId().toString(), message);


            // Assert
            boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);

            assertTrue(messageConsumed);
            assertThat(consumer.getPayload(), containsString(message));
        }
    }
}
