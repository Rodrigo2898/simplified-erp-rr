package com.ms.rr.produto_service.application.dto.in;

import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProduto(@NotBlank String nome,
                            @NotBlank String descricao,
                            @NotBlank String categoria,
                            BigDecimal preco,
                            @NotNull Long fornecedorId) {

    public ProdutoDomain toDomain() {
        return ProdutoDomain.create(nome, descricao, categoria, preco, fornecedorId);
    }
}
