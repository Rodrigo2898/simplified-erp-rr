package com.ms.rr.pessoa_service.application.dto.in;

import com.ms.rr.pessoa_service.domain.model.Endereco;
import com.ms.rr.pessoa_service.domain.model.TipoPessoa;

import java.time.LocalDate;
import java.util.List;

public record CreateCliente(String nome,
                            String email,
                            String telefone,
                            TipoPessoa tipoPessoa,
                            String cpf,
                            LocalDate dataCadastro,
                            List<Endereco> enderecos) {
}
