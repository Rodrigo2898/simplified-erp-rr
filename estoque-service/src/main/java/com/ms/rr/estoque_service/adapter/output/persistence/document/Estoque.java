package com.ms.rr.estoque_service.adapter.output.persistence.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "estoque")
public class Estoque {

    @Id
    private String id;
    private Long produtoId;
    private String skuCode;
    private Integer quantidade;
    private String tipoProduto;
    private LocalDateTime dataAtualizacao;

    public Estoque() {
    }

    public Estoque(String id, Long produtoId, String skuCode,
                   Integer quantidade, String tipoProduto, LocalDateTime dataAtualizacao) {
        this.id = id;
        this.produtoId = produtoId;
        this.skuCode = skuCode;
        this.quantidade = quantidade;
        this.tipoProduto = tipoProduto;
        this.dataAtualizacao = dataAtualizacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
