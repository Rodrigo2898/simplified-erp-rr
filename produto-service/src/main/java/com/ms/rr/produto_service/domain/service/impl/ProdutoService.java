package com.ms.rr.produto_service.domain.service.impl;

import com.ms.rr.produto_service.application.port.input.ProdutoUseCase;
import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService implements ProdutoUseCase {
    @Override
    public void salvar(ProdutoDomain produtoDomain) {

    }

    @Override
    public Page<ProdutoDomain> buscarPorNome(String nome, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ProdutoDomain> buscarTodos(Pageable pageable) {
        return null;
    }

    @Override
    public void excluir(Long id) {

    }
}
