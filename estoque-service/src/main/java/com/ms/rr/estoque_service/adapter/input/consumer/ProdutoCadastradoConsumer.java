package com.ms.rr.estoque_service.adapter.input.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.rr.estoque_service.domain.dto.in.CreateEstoque;
import com.ms.rr.estoque_service.domain.model.ProdutoCriadoEvent;
import com.ms.rr.estoque_service.domain.port.input.EstoqueUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

@Component
public class ProdutoCadastradoConsumer {

    private static final Logger log = LoggerFactory.getLogger(ProdutoCadastradoConsumer.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final EstoqueUseCase estoqueUseCase;

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload;

    public ProdutoCadastradoConsumer(EstoqueUseCase estoqueUseCase) {
        this.estoqueUseCase = estoqueUseCase;
    }


    @KafkaListener(
            topics = "${topicos.produto-cadastrado.cosumer.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Payload String jsonEvent) throws JsonProcessingException {
        log.info("Produto recebido: {}", jsonEvent);
        payload = jsonEvent;
        ProdutoCriadoEvent event = objectMapper.readValue(jsonEvent, ProdutoCriadoEvent.class);
        CreateEstoque createEstoque = new CreateEstoque(
                event.getNomeProduto(),
                "SKU"+new Random().nextInt(100),
                1,
                event.getTipoProduto()
        );
        latch.countDown();
        estoqueUseCase.salvar(createEstoque);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public String getPayload() {
        return payload;
    }
}
