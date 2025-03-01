package com.ms.rr.pessoa_service.domain.service.impl;

import com.ms.rr.pessoa_service.application.port.input.ClienteUseCase;
import com.ms.rr.pessoa_service.application.port.output.ClienteOutputPort;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteUseCase {

    private final ClienteOutputPort clienteOutputPort;

    public ClienteServiceImpl(ClienteOutputPort clienteOutputPort) {
        this.clienteOutputPort = clienteOutputPort;
    }

    @Override
    public void salvar(ClienteDomain domain) {
        clienteOutputPort.save(domain);
    }

    @Override
    public Optional<ClienteDomain> buscarPorId(Long id) {
        return Optional.of(clienteOutputPort.findById(id).orElseThrow());
    }

    @Override
    public List<ClienteDomain> buscarTodos() {
        return clienteOutputPort.findAll();
    }

    @Override
    public void excluir(Long id) {
        clienteOutputPort.deleteById(id);
    }
}
