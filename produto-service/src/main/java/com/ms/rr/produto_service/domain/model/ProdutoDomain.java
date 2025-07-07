package com.ms.rr.produto_service.domain.model;

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
                Integer.toUnsignedLong(new Random().nextInt()),
                nome,
                descricao,
                categoria,
                preco,
                fornecedorId
        );
    }
}
