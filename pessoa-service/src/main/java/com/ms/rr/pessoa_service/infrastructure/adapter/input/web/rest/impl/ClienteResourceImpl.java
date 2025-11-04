package com.ms.rr.pessoa_service.infrastructure.adapter.input.web.rest.impl;

import com.ms.rr.pessoa_service.application.api.ClienteApi;
import com.ms.rr.pessoa_service.application.dto.in.CreateCliente;
import com.ms.rr.pessoa_service.application.dto.in.UpdateCliente;
import com.ms.rr.pessoa_service.application.dto.out.ClienteResponse;
import com.ms.rr.pessoa_service.infrastructure.adapter.input.web.rest.PessoaResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteResourceImpl implements PessoaResource<CreateCliente, ClienteResponse, UpdateCliente, Long> {

    private final ClienteApi clienteApi;

    public ClienteResourceImpl(ClienteApi clienteApi) {
        this.clienteApi = clienteApi;
    }

    @Override
    public ResponseEntity<CreateCliente> create(CreateCliente createCliente) {
        clienteApi.create(createCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(createCliente);
    }

    @Override
    public ResponseEntity<List<ClienteResponse>> getAll() {
        return ResponseEntity.ok().body(clienteApi.list());
    }

    @Override
    public ResponseEntity<ClienteResponse> getById(Long id) {
        return ResponseEntity.ok().body(clienteApi.findById(id));
    }

    @Override
    public ResponseEntity<ClienteResponse> update(Long id, UpdateCliente updateCliente) {
        return ResponseEntity.ok().body(clienteApi.update(id, updateCliente));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        clienteApi.delete(id);
        return ResponseEntity.noContent().build();
    }
}
