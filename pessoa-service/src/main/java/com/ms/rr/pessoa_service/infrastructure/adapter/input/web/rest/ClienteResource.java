package com.ms.rr.pessoa_service.infrastructure.adapter.input.web.rest;

import com.ms.rr.pessoa_service.application.api.ClienteApi;
import com.ms.rr.pessoa_service.application.dto.in.CreateCliente;
import com.ms.rr.pessoa_service.application.dto.in.UpdateCliente;
import com.ms.rr.pessoa_service.application.dto.out.ClienteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteResource {

    private final ClienteApi clienteApi;

    public ClienteResource(ClienteApi clienteApi) {
        this.clienteApi = clienteApi;
    }

    @PostMapping
    public ResponseEntity<CreateCliente> create(@RequestBody CreateCliente dto) {
        clienteApi.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> getAll() {
        return ResponseEntity.ok().body(clienteApi.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(clienteApi.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> update(@PathVariable Long id, @RequestBody UpdateCliente dto) {
        return ResponseEntity.ok().body(clienteApi.update(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteApi.delete(id);
        return ResponseEntity.noContent().build();
    }
}
