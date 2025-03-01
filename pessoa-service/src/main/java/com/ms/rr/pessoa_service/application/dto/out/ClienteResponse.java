package com.ms.rr.pessoa_service.application.dto.out;

import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;
import com.ms.rr.pessoa_service.domain.model.TipoPessoaDomain;

import java.time.LocalDate;
import java.util.List;

public record ClienteResponse(Long id,
                              String nome,
                              String email,
                              String telefone,
                              TipoPessoaDomain tipoPessoa,
                              String cpf,
                              LocalDate dataCadastro,
                              List<EnderecoDomain> enderecos) {

    public static ClienteResponse fromDomain(ClienteDomain domain) {
        return new ClienteResponse(
                domain.getId(),
                domain.getNome(),
                domain.getEmail(),
                domain.getTelefone(),
                domain.getTipoPessoa(),
                domain.getCpf(),
                domain.getDataCadastro(),
                domain.getEnderecos());
    }
}
