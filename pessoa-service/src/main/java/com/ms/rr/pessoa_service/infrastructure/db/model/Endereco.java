package com.ms.rr.pessoa_service.infrastructure.db.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rua;
    private String cidade;
    private String estado;
    private String cep;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Pessoa cliente;
}
