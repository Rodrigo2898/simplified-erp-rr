package com.ms.rr.pessoa_service.domain.service.impl;

import com.ms.rr.pessoa_service.domain.model.Cliente;
import com.ms.rr.pessoa_service.domain.service.IPessoaService;

import java.util.List;
import java.util.Optional;

public class ClienteServiceImpl implements IPessoaService<Cliente> {

    @Override
    public Cliente salvar(Cliente pessoa) {
        return null;
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Cliente> buscarTodos() {
        return List.of();
    }

    @Override
    public void excluir(Long id) {

    }
}
