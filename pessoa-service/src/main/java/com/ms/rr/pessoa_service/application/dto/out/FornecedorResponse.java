package com.ms.rr.pessoa_service.application.dto.out;

import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;

public record FornecedorResponse(Long id,
                                 String nome,
                                 String email,
                                 String telefone,
                                 String cpnj,
                                 String razaoSocial) {

    public static FornecedorResponse fromDomain(FornecedorDomain domain) {
        return new FornecedorResponse(
                domain.id(),
                domain.nome(),
                domain.email(),
                domain.telefone(),
                domain.cnpj(),
                domain.razaoSocial());
    }
}
