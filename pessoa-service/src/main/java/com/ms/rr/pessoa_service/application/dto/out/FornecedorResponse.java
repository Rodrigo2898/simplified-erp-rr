package com.ms.rr.pessoa_service.application.dto.out;

import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;
import com.ms.rr.pessoa_service.domain.model.TipoPessoaDomain;

import java.util.List;

public record FornecedorResponse(Long id,
                                 String nome,
                                 String email,
                                 String telefone,
                                 TipoPessoaDomain tipoPessoa,
                                 String cpnj,
                                 String razaoSocial,
                                 List<EnderecoDomain> enderecos) {
}
