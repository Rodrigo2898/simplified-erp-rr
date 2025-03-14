package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity;

import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;
import com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity.enums.TipoPessoa;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("FORNECEDOR")
public class Fornecedor extends Pessoa {
    private String cnpj;
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
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        entity.setEmail(domain.getEmail());
        entity.setTelefone(domain.getTelefone());
        entity.setEnderecos(getEnderecoFromDomain(domain.getEnderecos()));
        entity.setCnpj(domain.getCnpj());
        entity.setRazaoSocial(domain.getRazaoSocial());
        return entity;
    }

    public FornecedorDomain toDomain() {
        return new FornecedorDomain(
                getId(),
                getNome(),
                getEmail(),
                getTelefone(),
                getTipo().toDomain(),
                getEnderecos().stream()
                        .map(Endereco::toDomain)
                        .toList(),
                getCnpj(),
                getRazaoSocial());
    }
}