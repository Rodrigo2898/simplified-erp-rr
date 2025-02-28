package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity;

import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rua;
    private String cidade;
    private String estado;
    private String cep;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    public Endereco() {
    }

    public Endereco(Long id, String rua, String cidade, String estado, String cep, Pessoa pessoa) {
        this.id = id;
        this.rua = rua;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.pessoa = pessoa;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public static Endereco fromDomain(EnderecoDomain domain) {
        Endereco endereco = new Endereco();

        endereco.setId(domain.getId());
        endereco.setRua(domain.getRua());
        endereco.setCidade(domain.getCidade());
        endereco.setEstado(domain.getEstado());
        endereco.setCep(domain.getCep());
        return endereco;
    }

    public EnderecoDomain toDomain() {
        return new EnderecoDomain(id, rua, cidade, estado, cep);
    }
}
