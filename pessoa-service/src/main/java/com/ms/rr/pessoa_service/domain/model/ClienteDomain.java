package com.ms.rr.pessoa_service.domain.model;

import java.time.LocalDate;
import java.util.Random;

public record ClienteDomain(Long id,
                            String nome,
                            String email,
                            String telefone,
                            String cpf,
                            LocalDate dataCadastro) {

    public static ClienteDomain create(String nome,
                                       String email,
                                       String telefone,
                                       String cpf,
                                       LocalDate dataCadastro) {
        return new ClienteDomain(
                new Random().nextLong(),
                nome,
                email,
                telefone,
                cpf,
                dataCadastro);
    }
}
