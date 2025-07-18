package com.ms.rr.pessoa_service.domain.model;

import java.util.Random;

public record FornecedorDomain(Long id,
                               String nome,
                               String email,
                               String telefone,
                               String cnpj,
                               String razaoSocial)  {
    public static FornecedorDomain create(String nome,
                                          String email,
                                          String telefone,
                                          String cnpj,
                                          String razaoSocial) {
        return new FornecedorDomain(
                Integer.toUnsignedLong(new Random().nextInt()),
                nome,
                email,
                telefone,
                cnpj,
                razaoSocial);
    }
}
