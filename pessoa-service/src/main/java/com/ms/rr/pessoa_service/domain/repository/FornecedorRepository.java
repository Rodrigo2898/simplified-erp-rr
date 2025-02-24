package com.ms.rr.pessoa_service.domain.repository;

import com.ms.rr.pessoa_service.domain.model.Fornecedor;

public interface FornecedorRepository extends PessoaRepository<Fornecedor, Long> {

    Fornecedor findFornecedorByCnpj(String cnpj);
}
