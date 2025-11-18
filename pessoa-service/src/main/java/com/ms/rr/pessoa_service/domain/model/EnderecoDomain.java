package com.ms.rr.pessoa_service.domain.model;

public record EnderecoDomain(String cep,
                             String nomeRua,
                             Integer numeroRua,
                             String bairro,
                             String cidade,
                             String estado) {
}
