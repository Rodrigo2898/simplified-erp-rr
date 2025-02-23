package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("FORNECEDOR")
public class Fornecedor extends Pessoa {
    private String cnpj;
    private String razaoSocial;
}