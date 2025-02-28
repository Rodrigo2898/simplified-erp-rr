package com.ms.rr.pessoa_service.application.dto.filter;

public record ClienteFilter(String nome,
                            String email,
                            String telefone,
                            String cpf) {
}
