package com.ms.rr.pessoa_service.factory;

import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;
import com.ms.rr.pessoa_service.domain.model.TipoPessoaDomain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateClienteDomainFactory {

    public static ClienteDomain buildWithOneItem() {
        return new ClienteDomain(
                new Random().nextLong(),
                "Goku",
                "goku@gmail.com",
                "999999999",
                TipoPessoaDomain.CLIENTE,
                createEndereos(),
                "00000000001",
                LocalDate.parse("2025-01-08"));
    }

    private static List<EnderecoDomain> createEndereos() {
        EnderecoDomain enderecoDomain = new EnderecoDomain();
        enderecoDomain.setId(new Random().nextLong());
        enderecoDomain.setRua("Rua");
        enderecoDomain.setCidade("Bairro");
        enderecoDomain.setEstado("Uf");
        enderecoDomain.setCep("77777");
        return List.of(enderecoDomain);
    }

}
