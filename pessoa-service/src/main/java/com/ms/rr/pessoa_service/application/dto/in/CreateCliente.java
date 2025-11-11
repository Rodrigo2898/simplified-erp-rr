package com.ms.rr.pessoa_service.application.dto.in;

import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record CreateCliente(@NotBlank(message = "Nome não pode ser vazio")
                            @Size(min = 3, max = 50)
                            String nome,
                            @NotBlank(message = "Email não pode ser vazio")
                            @Size(min = 3, max = 20)
                            @Email(message = "Email inválido")
                            String email,
                            @NotBlank(message = "Número de telefone não pode ser vazio")
                            @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", message = "Telefone inválido")
                            String telefone,
                            @NotBlank(message = "CPF não pode ser vazio")
                            @CPF(message = "CPF inválido")
                            String cpf,
                            LocalDate dataCadastro,
                            @NotBlank(message = "CEP deve ser fornecido")
                            String cep,
                            String nomeRua,
                            String numeroRua,
                            String bairro,
                            @NotBlank(message = "Cidade deve ser fornecida")
                            String cidade,
                            @NotBlank(message = "Estado deve ser fornecido")
                            String estado) {

    public ClienteDomain toDomain() {
        EnderecoDomain endereco = new EnderecoDomain(
                cep,
                nomeRua,
                numeroRua,
                bairro, cidade,
                estado
        );
        return ClienteDomain.create(nome, email, telefone, cpf, dataCadastro, endereco);
    }
}
