package com.ms.rr.pessoa_service.domain.dto.out;

import com.ms.rr.pessoa_service.domain.model.ClienteDomain;

import java.time.LocalDate;

public record ClienteResponse(Long id,
                              String nome,
                              String email,
                              String telefone,
                              String cpf,
                              LocalDate dataCadastro) {

    public static ClienteResponse fromDomain(ClienteDomain domain) {
        return new ClienteResponse(
                domain.id(),
                domain.nome(),
                domain.email(),
                domain.telefone(),
                domain.cpf(),
                domain.dataCadastro());
    }
}
