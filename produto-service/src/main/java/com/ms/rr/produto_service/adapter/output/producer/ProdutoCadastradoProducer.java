package com.ms.rr.produto_service.adapter.output.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.rr.produto_service.domain.model.ProdutoCriadoEvent;
import com.ms.rr.produto_service.domain.port.output.ProdutoCriadoOutputPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ProdutoCadastradoProducer implements ProdutoCriadoOutputPort {

    @Value("${topicos.produto-cadastrado.producer.topic}")
    private String topicoProdutoCadastrado;

    private final KafkaTemplate<String,String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public ProdutoCadastradoProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<ProdutoCriadoEvent> sendMessage(ProdutoCriadoEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            return Mono.fromFuture(() -> kafkaTemplate.send(topicoProdutoCadastrado, event.id().toString(), message))
                    .then(Mono.just(event));
        } catch (JsonProcessingException e) {
            return Mono.error(new RuntimeException("Erro ao serializar evento", e));
        }
    }
}
