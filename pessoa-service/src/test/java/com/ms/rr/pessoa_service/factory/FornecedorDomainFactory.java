package com.ms.rr.pessoa_service.factory;

import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;

public class FornecedorDomainFactory {

    public static FornecedorDomain createFornecedorDomain() {
        return FornecedorDomain.create(
                "Goku",
                "goku@gmail.com",
                "(99)999999999",
                "50.095.037/0001-19",
                "razao 1",
                EnderecoDomainFactory.createEnderecoDomain()
        );
    }
}
