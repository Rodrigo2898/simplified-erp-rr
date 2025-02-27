package com.ms.rr.pessoa_service.application.dto.in;

import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;
import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;
import com.ms.rr.pessoa_service.domain.model.TipoPessoa;

import java.util.List;

public record CreateFornecedor(String nome,
                               String email,
                               String telefone,
                               TipoPessoa tipoPessoa,
                               List<EnderecoDomain> enderecos,
                               String cnpj,
                               String razaoSocial) {

    public FornecedorDomain toDomain() {
        return FornecedorDomain.create(nome, email, telefone, tipoPessoa, enderecos, cnpj, razaoSocial);
    }
}
