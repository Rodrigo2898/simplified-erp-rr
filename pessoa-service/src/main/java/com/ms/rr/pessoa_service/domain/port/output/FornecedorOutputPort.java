package com.ms.rr.pessoa_service.domain.port.output;

import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;
import com.ms.rr.pessoa_service.domain.query.FornecedorQuery;

import java.util.List;
import java.util.Optional;

public interface FornecedorOutputPort extends BaseOutputPort<FornecedorDomain, Long> {

    FornecedorDomain findFornecedorByCnpj(String cnpj);

    default void deleteById(Long id) {
        FornecedorDomain entity = findById(id).orElseThrow();
        delete(entity);
    }

    void update(Long id, FornecedorDomain domain);

    boolean emailExists(String email);

    boolean cnpjExists(String cnpj);
}
