package com.ms.rr.pessoa_service.application.dto.out;

import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;
import com.ms.rr.pessoa_service.domain.model.TipoPessoaDomain;

public record FornecedorResponse(Long id,
                                 String nome,
                                 String email,
                                 String telefone,
                                 TipoPessoaDomain tipoPessoa,
                                 String cpnj,
                                 String razaoSocial) {

    public static FornecedorResponse fromDomain(FornecedorDomain domain) {
        return new FornecedorResponse(
                domain.getId(),
                domain.getNome(),
                domain.getEmail(),
                domain.getTelefone(),
                domain.getTipoPessoa(),
                domain.getCnpj(),
                domain.getRazaoSocial());
    }
}
