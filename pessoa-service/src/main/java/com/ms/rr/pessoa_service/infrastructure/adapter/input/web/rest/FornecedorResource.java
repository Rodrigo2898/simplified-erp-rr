package com.ms.rr.pessoa_service.infrastructure.adapter.input.web.rest;

import com.ms.rr.pessoa_service.application.api.FornecedorApi;
import com.ms.rr.pessoa_service.application.dto.in.CreateFornecedor;
import com.ms.rr.pessoa_service.application.dto.out.FornecedorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedor")
public class FornecedorResource {

    private final FornecedorApi fornecedorApi;

    public FornecedorResource(FornecedorApi fornecedorApi) {
        this.fornecedorApi = fornecedorApi;
    }

    @PostMapping
    public ResponseEntity<CreateFornecedor> create(@RequestBody CreateFornecedor dto) {
        fornecedorApi.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<FornecedorResponse>> getAll() {
        return ResponseEntity.ok().body(fornecedorApi.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(fornecedorApi.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fornecedorApi.delete(id);
        return ResponseEntity.noContent().build();
    }
}
