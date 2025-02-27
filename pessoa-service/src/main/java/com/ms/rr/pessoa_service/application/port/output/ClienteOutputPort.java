package com.ms.rr.pessoa_service.application.port.output;

import com.ms.rr.pessoa_service.domain.model.ClienteDomain;

public interface ClienteOutputPort extends PessoaOutputPort<ClienteDomain, Long> {

    ClienteDomain findClienteByCpf(String cpf);
}
