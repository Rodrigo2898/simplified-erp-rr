package com.ms.rr.produto_service.application.dto.in;

import com.ms.rr.produto_service.domain.model.ProdutoDomain;

import java.math.BigDecimal;

public record CreateProduto(String nome,
                            String descricao,
                            String categoria,
                            BigDecimal preco,
                            Long fornecedorId) {

    public ProdutoDomain toDomain() {
        return ProdutoDomain.create(nome, descricao, categoria, preco, fornecedorId);
    }
}
