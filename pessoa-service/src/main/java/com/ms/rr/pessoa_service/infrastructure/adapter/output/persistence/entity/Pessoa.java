package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity;

import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;
import com.ms.rr.pessoa_service.domain.model.TipoPessoaDomain;
import com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity.enums.TipoPessoa;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_pessoa")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Herança única para clientes e fornecedores
@DiscriminatorColumn(name = "tipo_pessoa")
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String nome;
    protected String email;
    protected String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa", insertable = false, updatable = false)
    protected TipoPessoa tipo;

    @OneToMany(mappedBy = "pessoa", cascade =  CascadeType.ALL)
    protected List<Endereco> enderecos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public TipoPessoa getTipo() {
        return tipo;
    }

    public void setTipo(TipoPessoa tipo) {
        this.tipo = tipo;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    protected static List<Endereco> getEnderecoFromDomain(List<EnderecoDomain> enderecoDomain) {
        List<Endereco> enderecos = enderecoDomain.
                stream().map(Endereco::fromDomain).toList();
        return enderecos;
    }

    protected static TipoPessoa getTipoFromDomain(TipoPessoaDomain tipoPessoa) {
        return switch (tipoPessoa) {
            case CLIENTE -> TipoPessoa.CLIENTE;
            case FORNECEDOR -> TipoPessoa.FORNECEDOR;
            default -> throw new IllegalArgumentException("Tipo desconhecido: " + tipoPessoa);
        };
    }
}
