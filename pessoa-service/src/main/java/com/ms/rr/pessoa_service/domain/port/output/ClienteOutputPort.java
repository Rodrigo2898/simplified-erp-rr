package com.ms.rr.pessoa_service.domain.port.output;

import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.domain.query.ClienteQuery;

import java.util.List;
import java.util.Optional;

public interface ClienteOutputPort extends BaseOutputPort<ClienteDomain, Long> {

    ClienteDomain findClienteByCpf(String cpf);

    default void deleteById(Long id) {
        ClienteDomain entity = findById(id).orElseThrow();
        delete(entity);
    }
}
