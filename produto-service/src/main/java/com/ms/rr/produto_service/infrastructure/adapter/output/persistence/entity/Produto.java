package com.ms.rr.produto_service.infrastructure.adapter.output.persistence.entity;

import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_produto")
public class Produto {

    @Id
    private Long id;
    private String nome;
    private String descricao;
    private String categoria;
    private BigDecimal preco;
    private Long fornecedorId;

    public Produto() {
    }

    public Produto(Long id, String nome, String descricao, String categoria, BigDecimal preco, Long fornecedorId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.fornecedorId = fornecedorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Long getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Long fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public static Produto fromDomain(ProdutoDomain domain) {
        Produto entity = new Produto();
        entity.setId(domain.id());
        entity.setNome(domain.nome());
        entity.setDescricao(domain.descricao());
        entity.setCategoria(domain.categoria());
        entity.setPreco(domain.preco());
        entity.setFornecedorId(domain.fornecedorId());
        return entity;
    }

    public ProdutoDomain toDomain() {
        return new ProdutoDomain(
                getId(),
                getNome(),
                getDescricao(),
                getCategoria(),
                getPreco(),
                getFornecedorId());
    }
}
