package com.ms.rr.pessoa_service.application.dto.in;

import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;
import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;


public record UpdateFornecedor(@Size(min = 3, max = 50)
                               String nome,
                               @Size(min = 3, max = 20)
                               @Email(message = "Email inválido")
                               String email,
                               @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", message = "Telefone inválido")
                               String telefone,
                               @CNPJ(message = "CNPJ inválido")
                               String cnpj,
                               @Size(min = 5, max = 20)
                               String razaoSocial,
                               String cep,
                               String nomeRua,
                               String numeroRua,
                               String bairro,
                               String cidade,
                               String estado) {

    public FornecedorDomain toDomain(Long id) {
        EnderecoDomain endereco = new EnderecoDomain(
                cep,
                nomeRua,
                numeroRua,
                bairro, cidade,
                estado
        );
        return FornecedorDomain.create(nome, email, telefone, cnpj, razaoSocial, endereco);
    }
}
