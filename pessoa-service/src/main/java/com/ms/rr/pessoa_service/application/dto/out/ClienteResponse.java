package com.ms.rr.pessoa_service.application.dto.out;

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
                domain.getId(),
                domain.getNome(),
                domain.getEmail(),
                domain.getTelefone(),
                domain.getCpf(),
                domain.getDataCadastro());
    }
}
