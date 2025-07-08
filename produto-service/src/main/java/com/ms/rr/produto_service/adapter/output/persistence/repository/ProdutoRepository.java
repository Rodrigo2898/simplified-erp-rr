package com.ms.rr.produto_service.adapter.output.persistence.repository;

import com.ms.rr.produto_service.adapter.output.persistence.document.Produto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProdutoRepository extends ReactiveMongoRepository<Produto, Long> {

    Flux<Produto> findAll(Pageable pageable);

    Flux<Produto> findAllByCategoria(String categoria, Pageable pageable);

    Mono<Long> countByCategoria(String categoria);
}
