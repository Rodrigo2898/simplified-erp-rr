package com.ms.rr.produto_service.infrastructure.adapter.output.persistence.entity;

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
}
