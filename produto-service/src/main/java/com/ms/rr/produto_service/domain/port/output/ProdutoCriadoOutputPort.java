package com.ms.rr.produto_service.domain.port.output;

import com.ms.rr.produto_service.domain.model.ProdutoCriadoEvent;
import reactor.core.publisher.Mono;

public interface ProdutoCriadoOutputPort {

    Mono<ProdutoCriadoEvent> sendMessage(ProdutoCriadoEvent event);
}
