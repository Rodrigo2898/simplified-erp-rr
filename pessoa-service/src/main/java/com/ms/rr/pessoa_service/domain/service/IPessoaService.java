package com.ms.rr.pessoa_service.domain.service;

import com.ms.rr.pessoa_service.domain.model.Pessoa;

import java.util.List;
import java.util.Optional;

public interface IPessoaService<T extends Pessoa> {

    T salvar(T pessoa);
    Optional<T> buscarPorId(Long id);
    List<T> buscarTodos();
    void excluir(Long id);
}
