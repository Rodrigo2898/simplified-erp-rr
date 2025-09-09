package com.ms.rr.estoque_service.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.rr.estoque_service.adapter.input.consumer.ProdutoCadastradoConsumer;
import com.ms.rr.estoque_service.domain.dto.in.CreateEstoque;
import com.ms.rr.estoque_service.domain.model.ProdutoCriadoEvent;
import com.ms.rr.estoque_service.domain.port.input.EstoqueUseCase;
import com.ms.rr.estoque_service.factory.EstoqueFactory;
import com.ms.rr.estoque_service.factory.ProdutoCriadoEventFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ProdutoCadastradoConsumerIT extends AbstractIntegrationTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @MockitoBean
    private EstoqueUseCase estoqueUseCase;

    private ProdutoCriadoEvent produtoCriadoEvent;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Captor
    private ArgumentCaptor<CreateEstoque> captor;


    @BeforeEach
    public void setUp() {
        produtoCriadoEvent = ProdutoCriadoEventFactory.buildProdutoCriadoEvent();
    }

    @Nested
    class ListenProdutoCriadoEvent {

        @Test
        void shouldListenProdutoCriadoEventSuccesfully() throws JsonProcessingException {
            // Arrange
            String payload = objectMapper.writeValueAsString(produtoCriadoEvent);
            var createEstoque = EstoqueFactory.createEstoque();

            doNothing().when(estoqueUseCase).salvar(captor.capture());

            // Act
            kafkaTemplate.send("produto_cadastrado_test", produtoCriadoEvent.getProdutoId().toString(), payload);

            // Assert
            await()
                    .pollInterval(Duration.ofSeconds(3))
                    .atMost(Duration.ofSeconds(10))
                    .untilAsserted(() -> {
                        assertEquals(createEstoque.nomeProduto(), produtoCriadoEvent.getNomeProduto());
                    });
        }
    }
}
