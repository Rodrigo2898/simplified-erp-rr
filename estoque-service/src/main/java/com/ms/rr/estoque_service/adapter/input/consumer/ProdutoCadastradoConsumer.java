package com.ms.rr.estoque_service.adapter.input.consumer;

import com.ms.rr.estoque_service.domain.dto.in.CreateEstoque;
import com.ms.rr.estoque_service.domain.model.ProdutoCriadoEvent;
import com.ms.rr.estoque_service.domain.port.input.EstoqueUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class ProdutoCadastradoConsumer {

    private static final Logger log = LoggerFactory.getLogger(ProdutoCadastradoConsumer.class);

    private final EstoqueUseCase estoqueUseCase;

    public ProdutoCadastradoConsumer(EstoqueUseCase estoqueUseCase) {
        this.estoqueUseCase = estoqueUseCase;
    }


    @KafkaListener(topics = "${topicos.produto-cadastrado.cosumer.topic}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void listen(@Payload List<ProdutoCriadoEvent> events) {
        log.info("Produto recebido: {}", events.toString());
        events.forEach(event -> {
            CreateEstoque createEstoque = new CreateEstoque(
                    event.getProdutoId(),
                    event.getNomeProduto(),
                    "SKU"+new Random().toString(),
                    1,
                    event.getTipoProduto()
            );
            estoqueUseCase.salvar(createEstoque);
        });
    }
}
