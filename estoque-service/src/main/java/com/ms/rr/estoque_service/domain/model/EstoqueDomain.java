package com.ms.rr.estoque_service.domain.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record EstoqueDomain(String id,
                            String nomeProduto,
                            String skuCode,
                            Integer quantidade,
                            String tipoProduto,
                            String dataAtualizacao) {

    public EstoqueDomain addQuantidadeAndUpdateDataAtualizacao(Integer quantidade) {
        return new EstoqueDomain(
                this.id,
                this.nomeProduto,
                this.skuCode,
                this.quantidade + quantidade,
                this.tipoProduto,
                novaDataAtualizacao()
        );
    }

    public static EstoqueDomain create(String nomeProduto, String skuCode, Integer quantidade,
                                String tipoProduto, String dataAtualizacao) {
        return new EstoqueDomain(
                UUID.randomUUID().toString(),
                nomeProduto,
                skuCode,
                quantidade,
                tipoProduto,
                dataAtualizacao
        );
    }

    private String novaDataAtualizacao() {
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.now().format(customFormatter);
    }
}
