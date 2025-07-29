package com.ms.rr.produto_service.factory;

import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.dto.in.UpdateProduto;
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

    public static UpdateProduto buildUpdateProduto() {
        return  new UpdateProduto(
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

    public static ProdutoDomain buildProdutoWithId(long id) {
        return new ProdutoDomain(
                id,
                "CAMISA Barcelona",
                "Camisa Barcelona 24/25",
                "Roupas",
                new BigDecimal("123.45"),
                123456789L);
    }

    public static ProdutoDomain buildProdutoWithCategoria(String categoria) {
        return new ProdutoDomain(
                new Random().nextLong(),
                "CAMISA Barcelona",
                "Camisa Barcelona 24/25",
                categoria,
                new BigDecimal("123.45"),
                123456789L);
    }

    public static String buildProdutoJson(String nome, String descricao, String preco, Long fornecedorId) {
        return String.format("""
            {
                "nome":"%s",
                "descricao":"%s",
                "categoria":"Roupas",
                "preco":"%s",
                "fornecedorId":%d
            }
        """, nome, descricao, preco, fornecedorId);
    }

    public static String buildProdutoWithNomeAndDescricao(String nome, String descricao) {
        return String.format("""
                    {
                        "nome":"%s",
                        "descricao":"%s",
                        "categoria":"Roupas",
                        "preco":"280.90",
                        "fornecedorId":672978073
                    }
                """, nome, descricao);
    }
}
