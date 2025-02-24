package com.ms.rr.pessoa_service.application.dto.out;

import com.ms.rr.pessoa_service.domain.model.Endereco;
import com.ms.rr.pessoa_service.domain.model.TipoPessoa;

import java.util.List;

public record FornecedorResponse(Long id,
                                 String nome,
                                 String email,
                                 String telefone,
                                 TipoPessoa tipoPessoa,
                                 String cpnj,
                                 String razaoSocial,
                                 List<Endereco> enderecos) {
}
