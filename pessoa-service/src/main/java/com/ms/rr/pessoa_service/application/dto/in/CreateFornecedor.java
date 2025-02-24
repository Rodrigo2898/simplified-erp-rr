package com.ms.rr.pessoa_service.application.dto.in;

import com.ms.rr.pessoa_service.domain.model.Endereco;
import com.ms.rr.pessoa_service.domain.model.TipoPessoa;

import java.util.List;

public record CreateFornecedor(String nome,
                               String email,
                               String telefone,
                               TipoPessoa tipoPessoa,
                               String cnpj,
                               String razaoSocial,
                               List<Endereco> enderecos) {
}
