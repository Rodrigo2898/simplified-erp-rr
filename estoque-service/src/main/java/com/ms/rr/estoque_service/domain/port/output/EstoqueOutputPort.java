package com.ms.rr.estoque_service.domain.port.output;

import com.ms.rr.estoque_service.domain.model.EstoqueDomain;

import java.util.Optional;

public interface EstoqueOutputPort {

    void save(EstoqueDomain estoqueDomain);

    Optional<EstoqueDomain> findByNomeProduto(String nomeProduto);
}
