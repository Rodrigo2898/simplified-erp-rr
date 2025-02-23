package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Pessoa {
    private String cpf;
    private LocalDate dataCadastro;
}
