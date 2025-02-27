package com.ms.rr.pessoa_service.domain.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class ClienteDomain extends PessoaDomain {

    private String cpf;
    private LocalDate dataCadastro;


    public ClienteDomain(Long id, String nome, String email, String telefone, TipoPessoa tipoPessoa,
                         List<EnderecoDomain> enderecos, String cpf, LocalDate dataCadastro) {
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

    public static ClienteDomain create(String nome, String email, String telefone, TipoPessoa tipoPessoa,
                                       List<EnderecoDomain> enderecos, String cpf, LocalDate dataCadastro) {
        return new ClienteDomain(
                new Random().nextLong(),
                nome,
                email,
                telefone,
                tipoPessoa,
                enderecos,
                cpf,
                dataCadastro);
    }
}
