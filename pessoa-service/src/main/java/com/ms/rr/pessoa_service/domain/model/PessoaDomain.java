package com.ms.rr.pessoa_service.domain.model;

import java.util.List;

public abstract class PessoaDomain {

    protected Long id;
    protected String nome;
    protected String email;
    protected String telefone;
    protected TipoPessoaDomain tipoPessoa;

    public PessoaDomain(Long id, String nome, String email, String telefone, TipoPessoaDomain tipoPessoa) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.tipoPessoa = tipoPessoa;
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

    public TipoPessoaDomain getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoaDomain tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }
}
