package com.ms.rr.produto_service.application.dto.out;

import com.ms.rr.produto_service.domain.model.ProdutoDomain;

import java.math.BigDecimal;

public record ProdutoResponse(Long id,
                              String nome,
                              String descricao,
                              String categoria,
                              BigDecimal preco,
                              Long fornecedorId) {

    public static ProdutoResponse fromDomain(ProdutoDomain domain) {
        return new ProdutoResponse(
                domain.id(),
                domain.nome(),
                domain.descricao(),
                domain.categoria(),
                domain.preco(),
                domain.fornecedorId());
    }
}
