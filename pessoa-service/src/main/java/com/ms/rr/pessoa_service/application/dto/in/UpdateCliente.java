package com.ms.rr.pessoa_service.application.dto.in;

import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record UpdateCliente(@Size(min = 3, max = 50)
                            String nome,
                            @Size(min = 3, max = 20)
                            @Email(message = "Email inválido")
                            String email,
                            @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", message = "Telefone inválido")
                            String telefone,
                            @CPF(message = "CPF inválido")
                            String cpf,
                            LocalDate dataCadastro,
                            String cep,
                            String nomeRua,
                            String numeroRua,
                            String bairro,
                            String cidade,
                            String estado) {

    public ClienteDomain toDomain(Long id) {
        EnderecoDomain endereco = new EnderecoDomain(
                cep,
                nomeRua,
                numeroRua,
                bairro, cidade,
                estado
        );
        return new ClienteDomain(id, nome, email, telefone, cpf, dataCadastro, endereco);
    }
}
