package com.ms.rr.estoque_service.domain.dto.out;

import com.ms.rr.estoque_service.domain.model.EstoqueDomain;

public record EstoqueResponse(String id,
                              String nomeProduto,
                              String skuCode,
                              Integer quantidade,
                              String tipoProduto,
                              String dataAtualizacao) {

    public static EstoqueResponse fromDomain(EstoqueDomain domain) {
        return new EstoqueResponse(
                domain.id(),
                domain.nomeProduto(),
                domain.skuCode(),
                domain.quantidade(),
                domain.tipoProduto(),
                domain.dataAtualizacao()
        );
    }
}
