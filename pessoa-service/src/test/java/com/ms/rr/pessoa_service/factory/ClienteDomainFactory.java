package com.ms.rr.pessoa_service.factory;

import com.ms.rr.pessoa_service.domain.model.ClienteDomain;

import java.time.LocalDate;

public class ClienteDomainFactory {

    public static ClienteDomain createClienteDomain() {
        return ClienteDomain.create(
                "Vegeta",
                "goku@gmail.com",
                "999999",
                "578.226.210-92",
                LocalDate.parse("2025-01-08"),
                EnderecoDomainFactory.createEnderecoDomain());
    }
}
