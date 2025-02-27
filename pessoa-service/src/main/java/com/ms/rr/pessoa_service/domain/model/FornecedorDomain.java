package com.ms.rr.pessoa_service.domain.model;

import java.util.List;
import java.util.Random;

public class FornecedorDomain extends PessoaDomain {

    private String cnpj;
    private String razaoSocial;

    public FornecedorDomain(Long id, String nome, String email, String telefone, TipoPessoa tipoPessoa,
                            List<EnderecoDomain> enderecos, String cnpj, String razaoSocial) {
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

    public static FornecedorDomain create(String nome, String email, String telefone, TipoPessoa tipoPessoa,
                                          List<EnderecoDomain> enderecos, String cnpj, String razaoSocial) {
        return new FornecedorDomain(
                new Random().nextLong(),
                nome,
                email,
                telefone,
                tipoPessoa,
                enderecos,
                cnpj,
                razaoSocial);
    }
}
