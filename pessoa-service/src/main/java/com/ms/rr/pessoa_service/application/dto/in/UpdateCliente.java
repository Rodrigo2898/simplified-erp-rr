package com.ms.rr.pessoa_service.application.dto.in;

import com.ms.rr.pessoa_service.domain.model.ClienteDomain;

import java.time.LocalDate;

public record UpdateCliente(String nome,
                            String email,
                            String telefone,
                            String cpf,
                            LocalDate dataCadastro) {

    public ClienteDomain toDomain(Long id) {
        return new ClienteDomain(id, nome, email, telefone, cpf, dataCadastro);
    }
}
