package com.ms.rr.pessoa_service.application.dto.in;

import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;
import com.ms.rr.pessoa_service.domain.model.TipoPessoaDomain;

import java.time.LocalDate;
import java.util.List;

public record CreateCliente(String nome,
                            String email,
                            String telefone,
                            List<EnderecoDomain> enderecos,
                            String cpf,
                            LocalDate dataCadastro) {

    public ClienteDomain toDomain() {
        return ClienteDomain.create(nome, email, telefone, cpf, dataCadastro, enderecos);
    }
}
