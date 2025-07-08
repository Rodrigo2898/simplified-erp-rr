package com.ms.rr.produto_service.domain.port.output;

import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProdutoOutputPort {

    Mono<ProdutoDomain> save(ProdutoDomain produto);

    Mono<ProdutoDomain> findById(Long id);

    Flux<ProdutoDomain> findAll(Pageable pageable);

    Flux<ProdutoDomain> findAllByCategoria(String categoria, Pageable pageable);

    Mono<Long> count();

    Mono<Long> countByCategoria(String categoria);

    Mono<Void> delete(Long id);
}
