package com.ms.rr.produto_service.application.dto.in;

import com.ms.rr.produto_service.domain.model.ProdutoDomain;

import java.math.BigDecimal;

public record UpdateProduto(String nome,
                            String descricao,
                            String categoria,
                            BigDecimal preco,
                            Long fornecedorId) {
    public ProdutoDomain toDomain(Long id) {
        return new ProdutoDomain(id, nome, descricao, categoria, preco, fornecedorId);
    }
}
