package com.ms.rr.pessoa_service.domain.service.impl;

import com.ms.rr.pessoa_service.application.dto.in.CreateFornecedor;
import com.ms.rr.pessoa_service.application.dto.out.FornecedorResponse;
import com.ms.rr.pessoa_service.application.port.input.FornecedorUseCase;
import com.ms.rr.pessoa_service.application.port.output.FornecedorOutputPort;

import java.util.List;
import java.util.Optional;

public class FornecedorServiceImpl implements FornecedorUseCase {

    private final FornecedorOutputPort fornecedorOutputPort;

    public FornecedorServiceImpl(FornecedorOutputPort fornecedorOutputPort) {
        this.fornecedorOutputPort = fornecedorOutputPort;
    }

    @Override
    public void salvar(CreateFornecedor pessoa) {
        fornecedorOutputPort.save(pessoa.toDomain());
    }

    @Override
    public Optional<FornecedorResponse> buscarPorId(Long id) {
        return Optional.empty();
    }

    @Override
    public List<FornecedorResponse> buscarTodos() {
        return List.of();
    }

    @Override
    public void excluir(Long id) {

    }
}
