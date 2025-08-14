package com.ms.rr.estoque_service.adapter.output.persistence.document;

import com.ms.rr.estoque_service.domain.model.EstoqueDomain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "estoque")
public class Estoque {

    @Id
    private String id;
    private String nomeProduto;
    private String skuCode;
    private Integer quantidade;
    private String tipoProduto;
    private String dataAtualizacao;

    public Estoque() {
    }

    public Estoque(String id, String nomeProduto, String skuCode,
                   Integer quantidade, String tipoProduto, String dataAtualizacao) {
        this.id = id;
        this.nomeProduto = nomeProduto;
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

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
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

    public String getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public static Estoque fromDomain(EstoqueDomain domain) {
        Estoque entity = new Estoque();
        entity.setId(domain.id());
        entity.setNomeProduto(domain.nomeProduto());
        entity.setSkuCode(domain.skuCode());
        entity.setQuantidade(domain.quantidade());
        entity.setTipoProduto(domain.tipoProduto());
        entity.setDataAtualizacao(domain.dataAtualizacao());
        return entity;
    }

    public EstoqueDomain toDomain() {
        return new EstoqueDomain(
                getId(),
                getNomeProduto(),
                getSkuCode(),
                getQuantidade(),
                getTipoProduto(),
                getDataAtualizacao()
        );
    }
}
