package com.ms.rr.estoque_service.adapter.input.consumer;

import com.ms.rr.estoque_service.domain.model.ProdutoCriadoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoCadastradoConsumer {

    private static final Logger log = LoggerFactory.getLogger(ProdutoCadastradoConsumer.class);


    @KafkaListener(topics = "${topicos.produto-cadastrado.cosumer.topic}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void listen(@Payload List<ProdutoCriadoEvent> event) {
        log.info("Produto recebido: {}", event.toString());
    }

}
