package com.ms.rr.pessoa_service.domain.service.impl;

import com.ms.rr.pessoa_service.application.dto.in.CreateFornecedor;
import com.ms.rr.pessoa_service.application.dto.out.FornecedorResponse;
import com.ms.rr.pessoa_service.application.port.input.FornecedorUseCase;
import com.ms.rr.pessoa_service.domain.repository.FornecedorRepository;

import java.util.List;
import java.util.Optional;

public class FornecedorServiceImpl implements FornecedorUseCase {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorServiceImpl(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public void salvar(CreateFornecedor pessoa) {
      
    }

    @Override
    public Optional<FornecedorResponse> buscarPorId(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<FornecedorResponse> buscarTodos() {
        return List.of();
    }

    @Override
    public void excluir(Long aLong) {

    }
}
