package com.ms.rr.pessoa_service.domain.pessoa;

import com.ms.rr.pessoa_service.domain.endereco.Endereco;

import java.util.List;

public abstract class Pessoa {

    protected Long id;
    protected String nome;
    protected String email;
    protected String telefone;
    protected TipoPessoa tipoPessoa;
    protected List<Endereco> enderecos;

    public Pessoa(Long id, String nome, String email, String telefone,
                  TipoPessoa tipoPessoa, List<Endereco> enderecos) {
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

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
}
