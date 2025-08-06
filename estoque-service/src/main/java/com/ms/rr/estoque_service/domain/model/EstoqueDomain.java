package com.ms.rr.estoque_service.domain.model;

import java.time.LocalDateTime;

public record EstoqueDomain(String id,
                            Long produtoId,
                            String nomeProduto,
                            String skuCode,
                            Integer quantidade,
                            String tipoProduto,
                            LocalDateTime dataAtualizacao) {
}
