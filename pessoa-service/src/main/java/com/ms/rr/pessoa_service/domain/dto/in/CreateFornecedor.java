package com.ms.rr.pessoa_service.domain.dto.in;

import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;
import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

public record CreateFornecedor(@NotBlank(message = "Nome não pode ser vazio")
                               @Size(min = 3, max = 50)
                               String nome,
                               @NotBlank(message = "Email não pode ser vazio")
                               @Size(min = 3, max = 20)
                               @Email(message = "Email inválido")
                               String email,
                               @NotBlank(message = "Número de telefone não pode ser vazio")
                               @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", message = "Telefone inválido")
                               String telefone,
                               @NotBlank(message = "CNPJ não pode ser vazio")
                               @CNPJ(message = "CNPJ inválido")
                               String cnpj,
                               @NotBlank(message = "Razão social não pode ser vazia")
                               @Size(min = 5, max = 20)
                               String razaoSocial,
                               @NotBlank(message = "CEP deve ser fornecido")
                               String cep,
                               String nomeRua,
                               Integer numeroRua,
                               String bairro,
                               @NotBlank(message = "Cidade deve ser fornecida")
                               String cidade,
                               @NotBlank(message = "Estado deve ser fornecido")
                               String estado) {

    public FornecedorDomain toDomain() {
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
