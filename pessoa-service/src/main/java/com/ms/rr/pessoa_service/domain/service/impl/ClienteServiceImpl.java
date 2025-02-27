package com.ms.rr.pessoa_service.domain.service.impl;

import com.ms.rr.pessoa_service.application.dto.in.CreateCliente;
import com.ms.rr.pessoa_service.application.dto.out.ClienteResponse;
import com.ms.rr.pessoa_service.application.port.input.ClienteUseCase;
import com.ms.rr.pessoa_service.application.port.output.ClienteOutputPort;

import java.util.List;
import java.util.Optional;

public class ClienteServiceImpl implements ClienteUseCase {

    private final ClienteOutputPort clienteOutputPort;

    public ClienteServiceImpl(ClienteOutputPort clienteOutputPort) {
        this.clienteOutputPort = clienteOutputPort;
    }

    @Override
    public void salvar(CreateCliente pessoa) {

    }

    @Override
    public Optional<ClienteResponse> buscarPorId(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<ClienteResponse> buscarTodos() {
        return List.of();
    }

    @Override
    public void excluir(Long aLong) {

    }
}
