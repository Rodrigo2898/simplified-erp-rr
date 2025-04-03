package com.ms.rr.pessoa_service.application.api;

import com.ms.rr.pessoa_service.application.dto.in.CreateCliente;
import com.ms.rr.pessoa_service.application.dto.in.UpdateCliente;
import com.ms.rr.pessoa_service.application.dto.out.ClienteResponse;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.domain.service.impl.ClienteServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteApi implements BaseApi<CreateCliente, ClienteResponse, Long> {

    private ClienteServiceImpl clienteService;

    public ClienteApi(@Lazy ClienteServiceImpl clienteService) {
        this.clienteService = clienteService;
    }

    @Override
    public void create(CreateCliente dto) {
        clienteService.salvar(dto.toDomain());
    }

    @Override
    public List<ClienteResponse> list() {
        return clienteService.buscarTodos()
                .stream()
                .map(ClienteResponse::fromDomain)
                .toList();
    }

    @Override
    public ClienteResponse findById(Long id) {
        return ClienteResponse.fromDomain(clienteService.buscarPorId(id));
    }

    public ClienteResponse update(Long id, UpdateCliente dto) {
        clienteService.salvar(dto.toDomain(id));
        var user = clienteService.buscarPorId(id);
        return ClienteResponse.fromDomain(user);
    }

    @Override
    public void delete(Long id) {
        clienteService.excluir(id);
    }
}
