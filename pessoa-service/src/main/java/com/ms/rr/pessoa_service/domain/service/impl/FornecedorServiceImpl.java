package com.ms.rr.pessoa_service.domain.service.impl;

import com.ms.rr.pessoa_service.domain.model.Fornecedor;
import com.ms.rr.pessoa_service.domain.service.IPessoaService;

import java.util.List;
import java.util.Optional;

public class FornecedorServiceImpl implements IPessoaService<Fornecedor> {

    @Override
    public Fornecedor salvar(Fornecedor pessoa) {
        return null;
    }

    @Override
    public Optional<Fornecedor> buscarPorId(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Fornecedor> buscarTodos() {
        return List.of();
    }

    @Override
    public void excluir(Long id) {

    }
}
