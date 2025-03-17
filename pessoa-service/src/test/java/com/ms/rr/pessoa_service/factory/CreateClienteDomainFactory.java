package com.ms.rr.pessoa_service.factory;

import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;
import com.ms.rr.pessoa_service.domain.model.PessoaDomain;
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
                "goku" + new Random().nextInt(1000) + "@gmail.com", // Email único
                "999999",
                TipoPessoaDomain.CLIENTE,
                createEndereos(),
                "00000000001",
                LocalDate.parse("2025-01-08"));
    }

    public static ClienteDomain buildWithOneItemNoId() {
// Use uma lista mutável para os endereços
        List<EnderecoDomain> enderecos = new ArrayList<>();
        EnderecoDomain endereco1 = new EnderecoDomain();
        endereco1.setRua("Rua A");
        endereco1.setCidade("São Paulo");
        endereco1.setEstado("SP");
        endereco1.setCep("12345-678");
        enderecos.add(endereco1);

        return new ClienteDomain(
                null,
                "Goku",
                "goku@gmail.com",
                "999999999",
                TipoPessoaDomain.CLIENTE,
                enderecos,
                "00000000001",
                LocalDate.parse("2025-01-08"));
    }

    private static List<EnderecoDomain> createEndereos() {
        List<EnderecoDomain> enderecos = new ArrayList<>();
        EnderecoDomain enderecoDomain = new EnderecoDomain();
        enderecoDomain.setId(new Random().nextLong());
        enderecoDomain.setRua("Rua");
        enderecoDomain.setCidade("Bairro");
        enderecoDomain.setEstado("Uf");
        enderecoDomain.setCep("77777");
//        enderecoDomain.setPessoa(clienteDomain);
        enderecos.add(enderecoDomain);
        return enderecos;
    }

}
