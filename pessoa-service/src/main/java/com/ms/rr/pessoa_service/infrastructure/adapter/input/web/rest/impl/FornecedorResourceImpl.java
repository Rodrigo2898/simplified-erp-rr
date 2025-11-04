package com.ms.rr.pessoa_service.infrastructure.adapter.input.web.rest.impl;

import com.ms.rr.pessoa_service.application.api.FornecedorApi;
import com.ms.rr.pessoa_service.application.dto.in.CreateFornecedor;
import com.ms.rr.pessoa_service.application.dto.in.UpdateFornecedor;
import com.ms.rr.pessoa_service.application.dto.out.FornecedorResponse;
import com.ms.rr.pessoa_service.infrastructure.adapter.input.web.rest.PessoaResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedor")
public class FornecedorResourceImpl implements PessoaResource<CreateFornecedor, FornecedorResponse, UpdateFornecedor, Long> {

    private final FornecedorApi fornecedorApi;

    public FornecedorResourceImpl(FornecedorApi fornecedorApi) {
        this.fornecedorApi = fornecedorApi;
    }

    @Override
    public ResponseEntity<CreateFornecedor> create(CreateFornecedor createFornecedor) {
        fornecedorApi.create(createFornecedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createFornecedor);
    }

    @Override
    public ResponseEntity<List<FornecedorResponse>> getAll() {
        return ResponseEntity.ok().body(fornecedorApi.list());
    }

    @Override
    public ResponseEntity<FornecedorResponse> getById(Long id) {
        return ResponseEntity.ok().body(fornecedorApi.findById(id));
    }

    @Override
    public ResponseEntity<FornecedorResponse> update(Long id, UpdateFornecedor updateFornecedor) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        fornecedorApi.delete(id);
        return ResponseEntity.noContent().build();
    }
}
