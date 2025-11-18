package com.ms.rr.pessoa_service.domain.dto.filter;

public record ClienteFilter(String nome,
                            String email,
                            String telefone,
                            String cpf) {
}
