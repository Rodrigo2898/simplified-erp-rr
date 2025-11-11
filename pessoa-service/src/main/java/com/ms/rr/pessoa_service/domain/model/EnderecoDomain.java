package com.ms.rr.pessoa_service.domain.model;

public record EnderecoDomain(String cep,
                             String nomeRua,
                             String numeroRua,
                             String bairro,
                             String cidade,
                             String estado) {
}
