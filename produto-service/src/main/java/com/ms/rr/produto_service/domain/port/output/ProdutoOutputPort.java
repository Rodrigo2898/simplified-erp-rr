package com.ms.rr.produto_service.domain.port.output;

import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface ProdutoOutputPort {

    Mono<Void> save(ProdutoDomain produto);

    ProdutoDomain findById(Long id);

    Page<ProdutoDomain> findAll(Pageable pageable);

    Page<ProdutoDomain> findAllByCategoria(String categoria, Pageable pageable);

    void delete(Long id);
}
