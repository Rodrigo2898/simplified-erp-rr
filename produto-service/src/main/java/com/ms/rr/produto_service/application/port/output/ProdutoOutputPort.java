package com.ms.rr.produto_service.application.port.output;

import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoOutputPort {

    void save(ProdutoDomain produtoDomain);

    ProdutoDomain findById(Long id);

    Page<ProdutoDomain> findAll(Pageable pageable);

    Page<ProdutoDomain> findByNomeContaining(String nome, Pageable pageable);

    void delete(Long id);
}
