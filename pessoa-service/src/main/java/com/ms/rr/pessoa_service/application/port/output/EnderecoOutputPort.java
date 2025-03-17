package com.ms.rr.pessoa_service.application.port.output;

import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;

import java.util.List;

public interface EnderecoOutputPort {
    void salvarEnderecos(List<EnderecoDomain> enderecos);
}
