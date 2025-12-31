package com.ms.rr.pessoa_service.factory;

import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;

public class EnderecoDomainFactory {

    public static EnderecoDomain createEnderecoDomain() {
        return new EnderecoDomain(
                "77777777",
                "rua1",
                1,
                "bairro 1",
                "Brasilia",
                "DF");
    }
}
