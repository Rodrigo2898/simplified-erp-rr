package com.ms.rr.estoque_service.domain.dto.in;

import com.ms.rr.estoque_service.domain.model.EstoqueDomain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record CreateEstoque(String nomeProduto,
                            String skuCode,
                            Integer quantidade,
                            String tipoProduto) {
    public EstoqueDomain toDomain() {
        return EstoqueDomain.create(nomeProduto, skuCode, quantidade, tipoProduto, LocalDateTime.now().format(customFormatter()));
    }

    private DateTimeFormatter customFormatter() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    }
}
