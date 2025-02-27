package com.ms.rr.pessoa_service.application.port.output;

import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;

public interface FornecedorOutputPort extends PessoaOutputPort<FornecedorDomain, Long> {

    FornecedorDomain findFornecedorByCnpj(String cnpj);
}
