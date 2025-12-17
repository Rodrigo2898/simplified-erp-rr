package com.ms.rr.pessoa_service.adapter.output.persistence.entity.vo;

import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco {

    private String cep;
    @Column(name = "nome_rua")
    private String nomeRua;
    @Column(name = "numero_rua")
    private Integer numeroRua;
    private String bairro;
    private String cidade;
    private String estado;

    public Endereco() {
    }

    public Endereco(String cep, String nomeRua, Integer numeroRua, String bairro, String cidade, String estado) {
        this.cep = cep;
        this.nomeRua = nomeRua;
        this.numeroRua = numeroRua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNomeRua() {
        return nomeRua;
    }

    public void setNomeRua(String nomeRua) {
        this.nomeRua = nomeRua;
    }

    public Integer getNumeroRua() {
        return numeroRua;
    }

    public void setNumeroRua(Integer numeroRua) {
        this.numeroRua = numeroRua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
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

    public static Endereco fromDomain(EnderecoDomain domain) {
        Endereco endereco = new Endereco();
        endereco.setCep(domain.cep());
        endereco.setNomeRua(domain.nomeRua());
        endereco.setNumeroRua(domain.numeroRua());
        endereco.setBairro(domain.bairro());
        endereco.setCidade(domain.cidade());
        endereco.setEstado(domain.estado());
        return endereco;
    }

    public EnderecoDomain toDomain() {
        return new EnderecoDomain(
                getCep(),
                getNomeRua(),
                getNumeroRua(),
                getBairro(),
                getCidade(),
                getEstado()
        );
    }
}
