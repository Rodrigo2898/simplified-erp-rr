package com.ms.rr.pessoa_service.adapter.output.persistence.entity;

import com.ms.rr.pessoa_service.adapter.output.persistence.entity.enums.TipoPessoa;
import com.ms.rr.pessoa_service.adapter.output.persistence.entity.vo.Endereco;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_pessoa")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Herança única para clientes e fornecedores
@DiscriminatorColumn(name = "tipo_pessoa")
public abstract class Pessoa {

    @Id
    protected Long id;

    protected String nome;
    protected String email;
    protected String telefone;

    @Embedded
    protected Endereco endereco;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa", insertable = false, updatable = false)
    protected TipoPessoa tipo;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public TipoPessoa getTipo() {
        return tipo;
    }

    public void setTipo(TipoPessoa tipo) {
        this.tipo = tipo;
    }

}
