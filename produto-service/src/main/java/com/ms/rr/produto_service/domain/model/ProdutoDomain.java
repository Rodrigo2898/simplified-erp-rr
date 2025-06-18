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
                Integer.toUnsignedLong(new Random().nextInt()),
                nome,
                descricao,
                categoria,
                preco,
                fornecedorId
        );
    }

    public Produto atualizar(String nome,
                             String descricao,
                             String categoria,
                             BigDecimal preco,
                             Long fornecedorId) {
        if (!this.fornecedorId.equals(fornecedorId)) {
            throw new RuntimeException("Fornecedor não é válido ou não criou o produto");
        }

        return new Produto(
                this.id,
                nome != null ? nome : this.nome,
                descricao != null ? descricao : this.descricao,
                categoria != null ? categoria : this.categoria,
                preco != null ? preco : this.preco,
                this.fornecedorId
                );
    }
}
