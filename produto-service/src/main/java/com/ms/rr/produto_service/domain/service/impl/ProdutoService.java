package com.ms.rr.produto_service.domain.service.impl;

import com.ms.rr.produto_service.application.port.input.ProdutoUseCase;
import com.ms.rr.produto_service.application.port.output.ProdutoOutputPort;
import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService implements ProdutoUseCase {

    private final ProdutoOutputPort produtoOutputPort;

    public ProdutoService(ProdutoOutputPort produtoOutputPort) {
        this.produtoOutputPort = produtoOutputPort;
    }

    @Override
    public void salvar(ProdutoDomain produtoDomain) {
        produtoOutputPort.save(produtoDomain);
    }

    @Override
    public ProdutoDomain buscarPorId(Long id) {
        return produtoOutputPort.findById(id);
    }

    @Override
    public Page<ProdutoDomain> buscarTodosProdutos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return produtoOutputPort.findAll(pageable);
    }

    @Override
    public Page<ProdutoDomain> buscarProdutosPorNome(String nome, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return produtoOutputPort.findByNomeContaining(nome, pageable);
    }

    @Override
    public void excluir(Long id) {
        produtoOutputPort.delete(id);
    }
}
