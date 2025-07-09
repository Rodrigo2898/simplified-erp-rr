package com.ms.rr.produto_service.domain.port.output;

import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProdutoOutputPort {

    Mono<ProdutoDomain> save(ProdutoDomain produto);

    Mono<ProdutoDomain> findById(Long id);

    Flux<ProdutoDomain> findAll();

    Flux<ProdutoDomain> findAllByCategoria(String categoria);

    Mono<Void> delete(Long id);
}
