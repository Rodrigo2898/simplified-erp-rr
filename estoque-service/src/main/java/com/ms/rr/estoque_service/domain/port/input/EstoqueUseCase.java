package com.ms.rr.estoque_service.domain.port.input;

import com.ms.rr.estoque_service.domain.dto.in.CreateEstoque;

public interface EstoqueUseCase {
    void salvar(CreateEstoque estoque);
}
