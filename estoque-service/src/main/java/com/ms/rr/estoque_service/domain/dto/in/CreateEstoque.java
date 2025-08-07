package com.ms.rr.estoque_service.domain.dto.in;

import java.time.LocalDateTime;

public record CreateEstoque(Long produtoId,
                            String nomeProduto,
                            String skuCode,
                            Integer quantidade,
                            String tipoProduto,
                            LocalDateTime dataAtualizacao) {
}
