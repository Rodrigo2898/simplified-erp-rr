package com.ms.rr.produto_service.factory;

import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.dto.out.ProdutoResponse;
import com.ms.rr.produto_service.domain.model.ProdutoDomain;

import java.math.BigDecimal;
import java.util.Random;

public class ProdutoFactory {

    public static ProdutoDomain buildProduto() {
        return new ProdutoDomain(
                987456321L,
                "CAMISA Barcelona",
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

    public static ProdutoResponse buildProdutoResponse() {
        return new ProdutoResponse(
                987456321L,
                "CAMISA Barcelona",
                "Camisa Barcelona 24/25",
                "Roupas",
                new BigDecimal("123.45"),
                123456789L
        );
    }
}
