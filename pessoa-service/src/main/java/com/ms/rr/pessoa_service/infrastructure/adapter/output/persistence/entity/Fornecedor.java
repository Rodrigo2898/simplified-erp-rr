package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity;

import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;
import com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity.enums.TipoPessoa;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("FORNECEDOR")
public class Fornecedor extends Pessoa {
    private String cnpj;
    @Column(name = "razao_social")
    private String razaoSocial;

    public Fornecedor() {
        this.setTipo(TipoPessoa.FORNECEDOR);
    }

    public Fornecedor(String cnpj, String razaoSocial) {
        this();
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

    public static Fornecedor fromDomain(FornecedorDomain domain) {
        Fornecedor entity = new Fornecedor();
        entity.setId(domain.id());
        entity.setNome(domain.nome());
        entity.setEmail(domain.email());
        entity.setTelefone(domain.telefone());
        entity.setCnpj(domain.cnpj());
        entity.setRazaoSocial(domain.razaoSocial());
        return entity;
    }

    public FornecedorDomain toDomain() {
        return new FornecedorDomain(
                getId(),
                getNome(),
                getEmail(),
                getTelefone(),
                getCnpj(),
                getRazaoSocial());
    }
}