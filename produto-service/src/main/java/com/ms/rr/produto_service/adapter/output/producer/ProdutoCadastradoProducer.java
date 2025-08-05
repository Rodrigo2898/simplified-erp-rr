package com.ms.rr.produto_service.adapter.output.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProdutoCriadoProducer {

    @Value("${topicos.produto-cadastrado.request.topic}")
    private String topic;
}
