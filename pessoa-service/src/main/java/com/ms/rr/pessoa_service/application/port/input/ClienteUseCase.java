package com.ms.rr.pessoa_service.application.port.input;

import com.ms.rr.pessoa_service.application.dto.in.CreateCliente;
import com.ms.rr.pessoa_service.application.dto.out.ClienteResponse;

public interface ClienteUseCase extends PessoaUseCase<CreateCliente, ClienteResponse, Long> {

}
