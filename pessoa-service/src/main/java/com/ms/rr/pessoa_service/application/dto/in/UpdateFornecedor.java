package com.ms.rr.pessoa_service.application.dto.in;

import java.time.LocalDate;

public record UpdateFornecedor(String nome,
                               String email,
                               String telefone,
                               String cpf,
                               LocalDate dataCadastro) {
}
