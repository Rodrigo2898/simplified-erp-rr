package com.ms.rr.pessoa_service.domain.port.input;

import com.ms.rr.pessoa_service.domain.dto.in.CreateCliente;
import com.ms.rr.pessoa_service.domain.dto.in.UpdateCliente;
import com.ms.rr.pessoa_service.domain.dto.out.ClienteResponse;;

public interface ClienteUseCase extends BaseUseCase<CreateCliente, UpdateCliente, ClienteResponse, Long> {

}
