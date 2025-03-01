package com.ms.rr.pessoa_service.application.api;

import com.ms.rr.pessoa_service.application.dto.in.CreateCliente;
import com.ms.rr.pessoa_service.application.dto.out.ClienteResponse;
import com.ms.rr.pessoa_service.domain.service.impl.ClienteServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteApi implements BaseApi<CreateCliente, ClienteResponse, Long> {

    private ClienteServiceImpl clienteService;

    public ClienteApi(ClienteServiceImpl clienteService) {
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
    public Optional<ClienteResponse> findById(Long id) {
        return clienteService.buscarPorId(id)
                .map(ClienteResponse::fromDomain);
    }

    @Override
    public void delete(Long id) {
        clienteService.excluir(id);
    }
}
