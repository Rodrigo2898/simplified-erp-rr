package com.ms.rr.pessoa_service.domain.service.impl;

import com.ms.rr.pessoa_service.application.port.input.ClienteUseCase;
import com.ms.rr.pessoa_service.application.port.output.ClienteOutputPort;
import com.ms.rr.pessoa_service.application.port.output.PessoaCriadaOutpuPort;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.domain.model.PessoaCriadaEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteServiceImpl implements ClienteUseCase {

    private final ClienteOutputPort clienteOutputPort;
    private final PessoaCriadaOutpuPort pessoaCriadaOutpuPort;

    public ClienteServiceImpl(ClienteOutputPort clienteOutputPort, PessoaCriadaOutpuPort pessoaCriadaOutpuPort) {
        this.clienteOutputPort = clienteOutputPort;
        this.pessoaCriadaOutpuPort = pessoaCriadaOutpuPort;
    }

    @Override
    public void salvar(ClienteDomain domain) {
        clienteOutputPort.save(domain);
        pessoaCriadaOutpuPort.sendMessage(eventFromClienteDomain(domain));
    }

    @Override
    public ClienteDomain buscarPorId(Long id) {
        return clienteOutputPort.findById(id).orElseThrow();
    }

    @Override
    public List<ClienteDomain> buscarTodos() {
        return clienteOutputPort.findAll();
    }

    @Override
    public void excluir(Long id) {
        clienteOutputPort.deleteById(id);
    }

    private PessoaCriadaEvent eventFromClienteDomain(ClienteDomain pessoa) {
        return new PessoaCriadaEvent(
                UUID.randomUUID(),
                pessoa.id(),
                pessoa.nome(),
                pessoa.email(),
                pessoa.telefone()
        );
    }
}
