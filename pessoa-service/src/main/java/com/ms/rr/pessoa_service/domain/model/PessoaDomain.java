package com.ms.rr.pessoa_service.domain.model;

import java.util.List;

public abstract class PessoaDomain {

    protected Long id;
    protected String nome;
    protected String email;
    protected String telefone;
    protected TipoPessoa tipoPessoa;
    protected List<EnderecoDomain> enderecos;

    public PessoaDomain(Long id, String nome, String email, String telefone,
                        TipoPessoa tipoPessoa, List<EnderecoDomain> enderecos) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.tipoPessoa = tipoPessoa;
        this.enderecos = enderecos;
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

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public List<EnderecoDomain> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoDomain> enderecos) {
        this.enderecos = enderecos;
    }
}
