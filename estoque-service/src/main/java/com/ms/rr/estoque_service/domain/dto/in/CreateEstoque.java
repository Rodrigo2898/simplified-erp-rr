package com.ms.rr.estoque_service.domain.dto.in;

import com.ms.rr.estoque_service.domain.model.EstoqueDomain;
import com.ms.rr.estoque_service.domain.utils.DateFormatterUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record CreateEstoque(String nomeProduto,
                            String skuCode,
                            Integer quantidade,
                            String tipoProduto) {
    public EstoqueDomain toDomain() {
        return EstoqueDomain.create(
                nomeProduto,
                skuCode,
                quantidade,
                tipoProduto,
                LocalDateTime.now().format(DateFormatterUtil.customFormatter()));
    }
}
