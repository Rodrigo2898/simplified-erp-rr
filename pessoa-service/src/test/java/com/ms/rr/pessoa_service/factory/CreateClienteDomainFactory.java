package com.ms.rr.pessoa_service.factory;

import com.ms.rr.pessoa_service.domain.model.ClienteDomain;

import java.time.LocalDate;
import java.util.Random;

public class CreateClienteDomainFactory {

    public static ClienteDomain buildWithOneItem() {
        return new ClienteDomain(
                new Random().nextLong(),
                "Goku",
                "goku" + new Random().nextInt(1000) + "@gmail.com",
                "999999",
                "00000000001",
                LocalDate.parse("2025-01-08"));
    }

    public static ClienteDomain buildWithOneItemNoId() {
        return new ClienteDomain(
                null,
                "Goku",
                "goku@gmail.com",
                "999999999",
                "00000000001",
                LocalDate.parse("2025-01-08"));
    }
}
