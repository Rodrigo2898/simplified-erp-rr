package com.ms.rr.pessoa_service.adapter.input.rest.impl;

import com.ms.rr.pessoa_service.domain.dto.in.CreateCliente;
import com.ms.rr.pessoa_service.domain.dto.in.UpdateCliente;
import com.ms.rr.pessoa_service.domain.dto.out.ClienteResponse;
import com.ms.rr.pessoa_service.adapter.input.rest.PessoaResource;
import com.ms.rr.pessoa_service.domain.port.input.ClienteUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteResourceImpl implements PessoaResource<CreateCliente, ClienteResponse, UpdateCliente, Long> {

    private final ClienteUseCase clienteUseCase;

    public ClienteResourceImpl(ClienteUseCase clienteUseCase) {
        this.clienteUseCase = clienteUseCase;
    }

    @Override
    public ResponseEntity<String> create(CreateCliente createCliente) {
        clienteUseCase.salvar(createCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente criado com sucesso!");
    }

    @Override
    public ResponseEntity<List<ClienteResponse>> getAll() {
        return ResponseEntity.ok().body(clienteUseCase.buscarTodos());
    }

    @Override
    public ResponseEntity<ClienteResponse> getById(Long id) {
        return ResponseEntity.ok().body(clienteUseCase.buscarPorId(id));
    }

    @Override
    public ResponseEntity<ClienteResponse> update(Long id, UpdateCliente updateCliente) {
        return ResponseEntity.ok().body(clienteUseCase.atualizar(id, updateCliente));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        clienteUseCase.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
