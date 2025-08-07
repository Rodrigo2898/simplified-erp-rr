package com.ms.rr.estoque_service.domain.model;

import java.util.UUID;

public class ProdutoCriadoEvent {
    private UUID id;
    private Long produtoId;
    private String nomeProduto;
    private String tipoProduto;

    public ProdutoCriadoEvent() {}

    public ProdutoCriadoEvent(UUID id, Long produtoId, String nomeProduto, String tipoProduto) {
        this.id = id;
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.tipoProduto = tipoProduto;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    @Override
    public String toString() {
        return "ProdutoCriadoEvent{" +
                "id=" + id +
                ", produtoId=" + produtoId +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", tipoProduto='" + tipoProduto + '\'' +
                '}';
    }
}
