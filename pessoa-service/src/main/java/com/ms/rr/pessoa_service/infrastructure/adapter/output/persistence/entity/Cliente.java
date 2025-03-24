package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity;

import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity.enums.TipoPessoa;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Pessoa {
    private String cpf;
    private LocalDate dataCadastro;

    public Cliente() {
        this.setTipo(TipoPessoa.CLIENTE);
    }

    public Cliente(String cpf, LocalDate dataCadastro) {
        this();
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

    @Override
    public String toString() {
        return "Cliente{" +
                "cpf='" + cpf + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", tipo=" + tipo +
                '}';
    }

    public static Cliente fromDomain(ClienteDomain domain) {
        Cliente entity = new Cliente();

//        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        entity.setEmail(domain.getEmail());
        entity.setTelefone(domain.getTelefone());
        entity.setCpf(domain.getCpf());
        entity.setDataCadastro(domain.getDataCadastro());
        System.out.println("Convertendo ClienteDomain -> Cliente: " + entity);
        return entity;
    }

    public ClienteDomain toDomain() {
        return new ClienteDomain(
                getNome(),
                getEmail(),
                getTelefone(),
                getCpf(),
                getDataCadastro());
    }
}
