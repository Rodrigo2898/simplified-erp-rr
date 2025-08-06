package com.ms.rr.produto_service.domain.port.output;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms.rr.produto_service.domain.model.ProdutoCriadoEvent;
import reactor.core.publisher.Mono;

public interface ProdutoCriadoOutputPort {

    Mono<String> sendMessage(ProdutoCriadoEvent event);
}
