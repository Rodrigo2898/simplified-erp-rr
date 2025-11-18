package com.ms.rr.pessoa_service.adapter.input.rest.impl;

import com.ms.rr.pessoa_service.domain.dto.in.CreateFornecedor;
import com.ms.rr.pessoa_service.domain.dto.in.UpdateFornecedor;
import com.ms.rr.pessoa_service.domain.dto.out.FornecedorResponse;
import com.ms.rr.pessoa_service.adapter.input.rest.PessoaResource;
import com.ms.rr.pessoa_service.domain.port.input.FornecedorUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedor")
public class FornecedorResourceImpl implements PessoaResource<CreateFornecedor, FornecedorResponse, UpdateFornecedor, Long> {

    private final FornecedorUseCase fornecedorUseCase;

    public FornecedorResourceImpl(FornecedorUseCase fornecedorUseCase) {
        this.fornecedorUseCase = fornecedorUseCase;
    }

    @Override
    public ResponseEntity<String> create(CreateFornecedor createFornecedor) {
        fornecedorUseCase.salvar(createFornecedor);
        return ResponseEntity.status(HttpStatus.CREATED).body("Fornecedor criado com sucesso!");
    }

    @Override
    public ResponseEntity<List<FornecedorResponse>> getAll() {
        return ResponseEntity.ok().body(fornecedorUseCase.buscarTodos());
    }

    @Override
    public ResponseEntity<FornecedorResponse> getById(Long id) {
        return ResponseEntity.ok().body(fornecedorUseCase.buscarPorId(id));
    }

    @Override
    public ResponseEntity<FornecedorResponse> update(Long id, UpdateFornecedor updateFornecedor) {
        return ResponseEntity.ok().body(fornecedorUseCase.atualizar(id, updateFornecedor));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        fornecedorUseCase.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
