package com.ms.rr.pessoa_service.domain.service.impl;

import com.ms.rr.pessoa_service.application.dto.in.CreateFornecedor;
import com.ms.rr.pessoa_service.application.dto.out.FornecedorResponse;
import com.ms.rr.pessoa_service.application.port.input.FornecedorUseCase;
import com.ms.rr.pessoa_service.application.port.output.FornecedorOutputPort;
import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorServiceImpl implements FornecedorUseCase {

    private final FornecedorOutputPort fornecedorOutputPort;

    public FornecedorServiceImpl(FornecedorOutputPort fornecedorOutputPort) {
        this.fornecedorOutputPort = fornecedorOutputPort;
    }

    @Override
    public void salvar(FornecedorDomain pessoa) {
        fornecedorOutputPort.save(pessoa);
    }

    @Override
    public Optional<FornecedorDomain> buscarPorId(Long id) {
        return Optional.of(fornecedorOutputPort.findById(id).orElseThrow());
    }

    @Override
    public List<FornecedorDomain> buscarTodos() {
        return fornecedorOutputPort.findAll();
    }

    @Override
    public void excluir(Long id) {
        fornecedorOutputPort.deleteById(id);
    }
}
