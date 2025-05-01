package com.ms.rr.produto_service.domain.model;

import java.math.BigDecimal;

public record ProdutoDomain(Long id,
                            String nome,
                            String descricao,
                            String categoria,
                            BigDecimal preco,
                            Long fornecedorId) {
}
