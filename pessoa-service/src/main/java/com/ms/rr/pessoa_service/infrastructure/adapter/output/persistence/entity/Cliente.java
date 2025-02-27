package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity;

import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;
import com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity.enums.TipoPessoa;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Pessoa {
    private String cpf;
    private LocalDate dataCadastro;

    public Cliente() {
    }

    public Cliente(String cpf, LocalDate dataCadastro) {
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

    public static Cliente fromDomain(ClienteDomain domain) {
        Cliente entity = new Cliente();

        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        entity.setEmail(domain.getEmail());
        entity.setTelefone(domain.getTelefone());
        entity.setTipo(getTipoFromDomain(domain.getTipoPessoa()));
        entity.setEnderecos(getEnderecoFromDomain(domain.getEnderecos()));
        entity.setCpf(domain.getCpf());
        entity.setDataCadastro(domain.getDataCadastro());
        return entity;
    }
}
