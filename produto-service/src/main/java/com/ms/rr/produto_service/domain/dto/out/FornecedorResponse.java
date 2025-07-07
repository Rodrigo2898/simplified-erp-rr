package com.ms.rr.produto_service.domain.dto.out;

public record FornecedorResponse(Long id,
                                 String nome,
                                 String email,
                                 String telefone,
                                 String cpnj,
                                 String razaoSocial) {
}
