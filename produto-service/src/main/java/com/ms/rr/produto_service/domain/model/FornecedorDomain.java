package com.ms.rr.produto_service.domain.model;

public record FornecedorDomain(Long id,
                               String nome,
                               String email,
                               String telefone,
                               String cnpj,
                               String razaoSocial) {
}
