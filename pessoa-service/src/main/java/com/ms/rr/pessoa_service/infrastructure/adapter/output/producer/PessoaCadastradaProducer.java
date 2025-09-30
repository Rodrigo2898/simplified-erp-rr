package com.ms.rr.pessoa_service.infrastructure.adapter.output.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.rr.pessoa_service.application.port.output.PessoaCriadaOutpuPort;
import com.ms.rr.pessoa_service.domain.model.PessoaCriadaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PessoaCadastradaProducer implements PessoaCriadaOutpuPort {

    private static final Logger log = LoggerFactory.getLogger(PessoaCadastradaProducer.class);

    @Value("${topicos.pessoa-cadastrada.producer.topic}")
    private String topicoProdutoCadastrado;

    private final KafkaTemplate<String,PessoaCriadaEvent> kafkaTemplate;


    private final ObjectMapper objectMapper;

    public PessoaCadastradaProducer(KafkaTemplate<String, PessoaCriadaEvent> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void sendMessage(PessoaCriadaEvent event) {
        kafkaTemplate.send(topicoProdutoCadastrado, event.id().toString(), event);
    }
}
