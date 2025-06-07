package com.ms.rr.produto_service.domain.service.impl;

import com.ms.rr.produto_service.application.port.input.ProdutoUseCase;
import com.ms.rr.produto_service.application.port.output.ProdutoOutputPort;
import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import com.ms.rr.produto_service.infrastructure.adapter.input.web.client.PessoaWebClientAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProdutoService implements ProdutoUseCase {

    private final ProdutoOutputPort produtoOutputPort;
    private final PessoaWebClientAdapter pessoaWebClientAdapter;

    public ProdutoService(ProdutoOutputPort produtoOutputPort, PessoaWebClientAdapter pessoaWebClientAdapter) {
        this.produtoOutputPort = produtoOutputPort;
        this.pessoaWebClientAdapter = pessoaWebClientAdapter;
    }


    public Mono<Void> salvarProduto(ProdutoDomain produtoDomain) {
        return pessoaWebClientAdapter.buscaFornecedorPoId(produtoDomain.fornecedorId())
                .flatMap(fornecedorDTO -> produtoOutputPort.saveProduto(produtoDomain));
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
    public Page<ProdutoDomain> buscarProdutosPorCategoria(String categoria, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return produtoOutputPort.findAllByCategoria(categoria, pageable);
    }

    @Override
    public void excluir(Long id) {
        produtoOutputPort.delete(id);
    }
}
