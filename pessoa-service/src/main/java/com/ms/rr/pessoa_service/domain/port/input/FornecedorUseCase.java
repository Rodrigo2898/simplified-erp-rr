package com.ms.rr.pessoa_service.domain.port.input;

import com.ms.rr.pessoa_service.domain.dto.in.CreateFornecedor;
import com.ms.rr.pessoa_service.domain.dto.in.UpdateFornecedor;
import com.ms.rr.pessoa_service.domain.dto.out.FornecedorResponse;
import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;

public interface FornecedorUseCase extends BaseUseCase<CreateFornecedor, UpdateFornecedor, FornecedorResponse, Long> {

    FornecedorResponse buscarPorCnpj(String cnpj);
}
