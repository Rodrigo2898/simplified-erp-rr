package com.ms.rr.estoque_service.domain.dto.in;

import com.ms.rr.estoque_service.domain.model.EstoqueDomain;

import java.time.LocalDateTime;

public record CreateEstoque(Long produtoId,
                            String nomeProduto,
                            String skuCode,
                            Integer quantidade,
                            String tipoProduto) {
    public EstoqueDomain toDomain() {
        return EstoqueDomain.create(produtoId, nomeProduto, skuCode, quantidade, tipoProduto, LocalDateTime.now());
    }
}
