package com.ms.rr.pessoa_service.domain.service;

import com.ms.rr.pessoa_service.domain.dto.in.CreateCliente;
import com.ms.rr.pessoa_service.domain.dto.in.UpdateCliente;
import com.ms.rr.pessoa_service.domain.dto.out.ClienteResponse;
import com.ms.rr.pessoa_service.domain.exception.BaseErrorMessage;
import com.ms.rr.pessoa_service.domain.exception.CPFExistsException;
import com.ms.rr.pessoa_service.domain.exception.ClienteNotFoundException;
import com.ms.rr.pessoa_service.domain.exception.EmailExistsException;
import com.ms.rr.pessoa_service.domain.port.input.ClienteUseCase;
import com.ms.rr.pessoa_service.domain.port.output.ClienteOutputPort;
import com.ms.rr.pessoa_service.domain.port.output.PessoaCriadaOutpuPort;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.domain.model.PessoaCriadaEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public void salvar(CreateCliente createCliente) {
        if (clienteOutputPort.emailExists(createCliente.email())) {
            throw new EmailExistsException(BaseErrorMessage.EXISTS_PESSOA_EMAIL.getMessage());
        }

        if (clienteOutputPort.cpfExists(createCliente.cpf())) {
            throw new CPFExistsException(BaseErrorMessage.EXISTS_PESSOA_CLIENTE_CPF.getMessage());
        }

        clienteOutputPort.save(createCliente.toDomain());
        pessoaCriadaOutpuPort.sendMessage(eventFromClienteDomain(createCliente.toDomain()));
    }

    @Override
    public ClienteResponse buscarPorId(Long id) {
        return clienteOutputPort.findById(id)
                .map(ClienteResponse::fromDomain)
                .orElseThrow(() -> new ClienteNotFoundException(BaseErrorMessage.CLIENTE_NOT_FOUND.getMessage()));
    }

    @Override
    public List<ClienteResponse> buscarTodos() {
        return clienteOutputPort.findAll()
                .stream().map(ClienteResponse::fromDomain)
                .toList();
    }


    @Transactional
    @Override
    public void atualizar(Long id, UpdateCliente updateCliente) {
        if (clienteOutputPort.findById(id).isEmpty()) {
            throw new ClienteNotFoundException(BaseErrorMessage.CLIENTE_NOT_FOUND.getMessage());
        }

        clienteOutputPort.update(id, updateCliente.toDomain(id));
    }

    @Transactional
    @Override
    public void excluir(Long id) {
        if (clienteOutputPort.findById(id).isEmpty()) {
            throw new ClienteNotFoundException(BaseErrorMessage.CLIENTE_NOT_FOUND.getMessage());
        }

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
