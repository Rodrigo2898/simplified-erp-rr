package com.ms.rr.pessoa_service.application.dto.out;

import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;
import com.ms.rr.pessoa_service.domain.model.TipoPessoa;

import java.time.LocalDate;
import java.util.List;

public record ClienteResponse(Long id,
                              String nome,
                              String email,
                              String telefone,
                              TipoPessoa tipoPessoa,
                              String cpf,
                              LocalDate dataCadastro,
                              List<EnderecoDomain> enderecos) {
}
