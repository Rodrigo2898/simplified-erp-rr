package com.ms.rr.pessoa_service.domain.model;

import java.util.List;

public class Fornecedor extends Pessoa{

    private String cnpj;
    private String razaoSocial;

    public Fornecedor(Long id, String nome, String email, String telefone, TipoPessoa tipoPessoa,
                      List<Endereco> enderecos, String cnpj, String razaoSocial) {
        super(id, nome, email, telefone, tipoPessoa, enderecos);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
}
