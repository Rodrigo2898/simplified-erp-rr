package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.repository;

import com.ms.rr.pessoa_service.adapter.output.persistence.repository.impl.SQLClienteRepository;
import com.ms.rr.pessoa_service.api.output.ClienteOutputPortTest;
import com.ms.rr.pessoa_service.domain.port.output.ClienteOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(SQLClienteRepository.class)
class SQLClienteRepositoryTest extends ClienteOutputPortTest {

    @Autowired
    ClienteOutputPort clienteOutputPort;

    @Override
    public ClienteOutputPort getClienteOutputPort() {
        return clienteOutputPort;
    }
}