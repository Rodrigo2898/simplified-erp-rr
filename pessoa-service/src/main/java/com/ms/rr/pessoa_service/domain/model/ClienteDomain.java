package com.ms.rr.pessoa_service.domain.model;

import java.time.LocalDate;
import java.util.Random;

public record ClienteDomain(Long id,
                            String nome,
                            String email,
                            String telefone,
                            String cpf,
                            LocalDate dataCadastro,
                            EnderecoDomain endereco) {

    public static ClienteDomain create(String nome,
                                       String email,
                                       String telefone,
                                       String cpf,
                                       LocalDate dataCadastro,
                                       EnderecoDomain endereco) {
        return new ClienteDomain(
                Integer.toUnsignedLong(new Random().nextInt()),
                nome,
                email,
                telefone,
                cpf,
                dataCadastro,
                endereco);
    }
}
