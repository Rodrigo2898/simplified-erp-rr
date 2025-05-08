package com.ms.rr.produto_service.application.port.output;

import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoOutputPort {
    Page<ProdutoDomain> findAll(Pageable pageable);

    Page<ProdutoDomain> findByNomeContaining(String nome, Pageable pageable);
}
