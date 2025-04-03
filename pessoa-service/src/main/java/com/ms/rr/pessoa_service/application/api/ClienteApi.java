package com.ms.rr.pessoa_service.application.api;

import com.ms.rr.pessoa_service.application.dto.in.CreateCliente;
import com.ms.rr.pessoa_service.application.dto.in.UpdateCliente;
import com.ms.rr.pessoa_service.application.dto.out.ClienteResponse;
import com.ms.rr.pessoa_service.application.port.input.ClienteUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteApi implements BaseApi<CreateCliente, ClienteResponse, Long> {

    private final ClienteUseCase clienteUseCase;

    public ClienteApi(ClienteUseCase clienteUseCase) {
        this.clienteUseCase = clienteUseCase;
    }

    @Override
    public void create(CreateCliente dto) {
        clienteUseCase.salvar(dto.toDomain());
    }

    @Override
    public List<ClienteResponse> list() {
        return clienteUseCase.buscarTodos()
                .stream()
                .map(ClienteResponse::fromDomain)
                .toList();
    }

    @Override
    public ClienteResponse findById(Long id) {
        return ClienteResponse.fromDomain(clienteUseCase.buscarPorId(id));
    }

    public ClienteResponse update(Long id, UpdateCliente dto) {
        clienteUseCase.salvar(dto.toDomain(id));
        var user = clienteUseCase.buscarPorId(id);
        return ClienteResponse.fromDomain(user);
    }

    @Override
    public void delete(Long id) {
        clienteUseCase.excluir(id);
    }
}
