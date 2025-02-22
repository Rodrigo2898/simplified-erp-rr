package com.ms.rr.pessoa_service.domain.model;

import java.time.LocalDate;
import java.util.List;

public class Cliente extends Pessoa {

    private String cpf;
    private LocalDate dataCadastro;


    public Cliente(Long id, String nome, String email, String telefone, TipoPessoa tipoPessoa,
                   List<Endereco> enderecos, String cpf, LocalDate dataCadastro) {
        super(id, nome, email, telefone, tipoPessoa, enderecos);
        this.cpf = cpf;
        this.dataCadastro = dataCadastro;
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
}
