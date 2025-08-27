package com.ms.rr.estoque_service.domain.port.output;

import com.ms.rr.estoque_service.domain.model.EstoqueDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface EstoqueOutputPort {

    void save(EstoqueDomain estoqueDomain);

    Optional<EstoqueDomain> findById(String id);

    Optional<EstoqueDomain> findByNomeProduto(String nomeProduto);

    Page<EstoqueDomain> findAll(PageRequest pageRequest);

    Page<EstoqueDomain> findAllByTipo(String tipoProduto, PageRequest pageRequest);

    void delete(EstoqueDomain estoque);
}
