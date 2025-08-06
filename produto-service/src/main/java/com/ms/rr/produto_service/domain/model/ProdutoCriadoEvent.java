package com.ms.rr.produto_service.domain.model;

import java.util.UUID;

public record ProdutoCriadoEvent(UUID id,
                                 Long produtoId,
                                 String nomeProduto,
                                 String tipoProduto) {

    public static ProdutoCriadoEvent fromDomain(ProdutoDomain domain) {
        return new ProdutoCriadoEvent(
                UUID.randomUUID(),
                domain.id(),
                domain.nome(),
                domain.categoria()
        );
    }
}
