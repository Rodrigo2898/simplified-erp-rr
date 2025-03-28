package com.ms.rr.pessoa_service.application.dto.in;

import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;

public record CreateFornecedor(String nome,
                               String email,
                               String telefone,
                               String cnpj,
                               String razaoSocial) {

    public FornecedorDomain toDomain() {
        return FornecedorDomain.create(nome, email, telefone, cnpj, razaoSocial);
    }
}
