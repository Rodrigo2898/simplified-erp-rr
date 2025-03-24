package com.ms.rr.pessoa_service.domain.model;

import java.time.LocalDate;
import java.util.Random;

public class ClienteDomain {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private LocalDate dataCadastro;

    public ClienteDomain() {
    }

    public ClienteDomain(Long id, String nome, String email, String telefone, String cpf, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.dataCadastro = dataCadastro;
    }

    public ClienteDomain(String nome, String email, String telefone, String cpf, LocalDate dataCadastro) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.dataCadastro = dataCadastro;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public static ClienteDomain create(String nome, String email, String telefone,
                                       String cpf, LocalDate dataCadastro) {
        return new ClienteDomain(
                new Random().nextLong(),
                nome,
                email,
                telefone,
                cpf,
                dataCadastro);
    }
}
