package com.ms.rr.estoque_service.domain.model;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public record EstoqueDomain(String id,
                            Long produtoId,
                            String nomeProduto,
                            String skuCode,
                            Integer quantidade,
                            String tipoProduto,
                            LocalDateTime dataAtualizacao) {

    public EstoqueDomain addQuantidade(Integer quantidade) {
        return new EstoqueDomain(
                this.id,
                this.produtoId,
                this.nomeProduto,
                this.skuCode,
                this.quantidade + quantidade,
                this.tipoProduto,
                this.dataAtualizacao
        );
    }

    public static EstoqueDomain create(Long produtoId, String nomeProduto, String skuCode, Integer quantidade,
                                String tipoProduto, LocalDateTime dataAtualizacao) {
        return new EstoqueDomain(
                UUID.randomUUID().toString(),
                produtoId,
                nomeProduto,
                skuCode,
                quantidade,
                tipoProduto,
                dataAtualizacao
        );
    }
}
