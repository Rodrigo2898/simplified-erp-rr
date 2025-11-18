package com.ms.rr.pessoa_service.domain.port.output;

import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;
import com.ms.rr.pessoa_service.domain.query.FornecedorQuery;

import java.util.List;
import java.util.Optional;

public interface FornecedorOutputPort extends BaseOutputPort<FornecedorDomain, Long> {

    FornecedorDomain findFornecedorByCnpj(String cnpj);

    List<FornecedorDomain> find(FornecedorQuery fornecedorQuery);

    default List<FornecedorDomain> findAll() {
        return find(new FornecedorQuery.Builder().build());
    }

    Optional<FornecedorDomain> findById(Long id);

    default void deleteById(Long id) {
        FornecedorDomain entity = findById(id).orElseThrow();
        delete(entity);
    }
}
