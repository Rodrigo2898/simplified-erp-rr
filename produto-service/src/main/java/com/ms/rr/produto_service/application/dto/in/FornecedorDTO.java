package com.ms.rr.produto_service.application.dto.in;

public record FornecedorDTO(Long id,
                            String nome,
                            String email,
                            String telefone,
                            String cnpj,
                            String razaoSocial) {
}
