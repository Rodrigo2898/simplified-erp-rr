package com.ms.rr.pessoa_service.application.port.input;

import com.ms.rr.pessoa_service.application.dto.in.CreateFornecedor;
import com.ms.rr.pessoa_service.application.dto.out.FornecedorResponse;

public interface FornecedorUseCase extends PessoaUseCase<CreateFornecedor, FornecedorResponse, Long> {
}
