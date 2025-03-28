package com.ms.rr.pessoa_service.domain.query;

import org.inferred.freebuilder.FreeBuilder;

import java.util.Optional;
import java.util.Set;

@FreeBuilder
public interface FornecedorQuery {

    Optional<Set<Long>> ids();
    Optional<String> nome();
    Optional<String> cnpj();

    class Builder extends FornecedorQuery_Builder {

    }
}
