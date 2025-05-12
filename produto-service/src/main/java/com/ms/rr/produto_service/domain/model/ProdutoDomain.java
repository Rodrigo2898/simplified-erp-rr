package com.ms.rr.produto_service.domain.model;

import com.ms.rr.produto_service.infrastructure.adapter.output.persistence.entity.Produto;

import java.math.BigDecimal;
import java.util.Random;

public record ProdutoDomain(Long id,
                            String nome,
                            String descricao,
                            String categoria,
                            BigDecimal preco,
                            Long fornecedorId) {

    public static ProdutoDomain create(String nome,
                                       String descricao,
                                       String categoria,
                                       BigDecimal preco,
                                       Long fornecedorId) {
        return new ProdutoDomain(
                new Random().nextLong(),
                nome,
                descricao,
                categoria,
                preco,
                fornecedorId
        );
    }
}
