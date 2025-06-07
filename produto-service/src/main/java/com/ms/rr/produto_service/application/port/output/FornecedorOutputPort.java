package com.ms.rr.produto_service.application.port.output;

import com.ms.rr.produto_service.domain.model.FornecedorDomain;
import reactor.core.publisher.Mono;

public interface FornecedorOutputPort {
    Mono<FornecedorDomain> findFornecedorById(Long id);
}
