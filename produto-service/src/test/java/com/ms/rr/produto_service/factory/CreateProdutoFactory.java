package com.ms.rr.produto_service.factory;

import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.model.ProdutoDomain;

import java.math.BigDecimal;

public class CreateProdutoFactory {

    public static ProdutoDomain createProdutoDomain() {
        return ProdutoDomain
                .create("CAMISA Barcelona",
                        "Camisa Barcelona 24/25",
                        "Roupas",
                        new BigDecimal("123.45"),
                        123456789L);
    }

    public static CreateProduto createProduto() {
        return new CreateProduto(
                "CAMISA Barcelona",
                "Camisa Barcelona 24/25",
                "Roupas",
                new BigDecimal("123.45"),
                123456789L
        );
    }
}
