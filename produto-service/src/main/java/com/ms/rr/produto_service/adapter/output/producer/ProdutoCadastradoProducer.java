package com.ms.rr.produto_service.adapter.output.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProdutoCadastradoProducer {

    @Value("${topicos.produto-cadastrado.request.topic}")
    private String topicoProdutoCadastrado;

    private final KafkaTemplate<String,String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public ProdutoCadastradoProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public String sendMessage(ProdutoDomain produtoDomain) throws JsonProcessingException {
        String orderAsMessage = objectMapper.writeValueAsString(produtoDomain);
        kafkaTemplate.send(topicoProdutoCadastrado, orderAsMessage);
        return orderAsMessage;
    }
}
