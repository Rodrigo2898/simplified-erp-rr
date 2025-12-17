package com.ms.rr.pessoa_service.adapter.output.persistence.entity;

import com.ms.rr.pessoa_service.adapter.output.persistence.entity.enums.TipoPessoa;
import com.ms.rr.pessoa_service.adapter.output.persistence.entity.vo.Endereco;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Pessoa {
    private String cpf;
    @Column(name = "data_cadastro")
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
        entity.setId(domain.id());
        entity.setNome(domain.nome());
        entity.setEmail(domain.email());
        entity.setTelefone(domain.telefone());
        entity.setCpf(domain.cpf());
        entity.setDataCadastro(domain.dataCadastro());
        entity.setEndereco(Endereco.fromDomain(domain.endereco()));
        return entity;
    }

    public ClienteDomain toDomain() {
        return new ClienteDomain(
                getId(),
                getNome(),
                getEmail(),
                getTelefone(),
                getCpf(),
                getDataCadastro(),
                getEndereco().toDomain());
    }
}
