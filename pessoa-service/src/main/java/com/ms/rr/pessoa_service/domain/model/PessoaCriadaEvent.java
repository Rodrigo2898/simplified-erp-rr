package com.ms.rr.pessoa_service.domain.model;

import java.util.UUID;

public record PessoaCriadaEvent(UUID id,
                                Long pessoaId,
                                String nome,
                                String email,
                                String telefone) {
}
