package com.ms.rr.notification_service.domain.model;

import java.util.UUID;

public class PessoaCriadaEvent {

    private UUID id;
    private Long pessoaId;
    private String nome;
    private String email;
    private String telefone;

    public PessoaCriadaEvent() {
    }

    public PessoaCriadaEvent(UUID id, Long pessoaId, String nome, String email, String telefone) {
        this.id = id;
        this.pessoaId = pessoaId;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
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
}
