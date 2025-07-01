package com.ms.rr.produto_service.domain.service.impl;

import com.ms.rr.produto_service.application.port.input.ProdutoUseCase;
import com.ms.rr.produto_service.application.port.output.ProdutoOutputPort;
import com.ms.rr.produto_service.domain.exception.FornecedorNotFoundException;
import com.ms.rr.produto_service.domain.exception.ProdutoNotFoundException;
import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import com.ms.rr.produto_service.infrastructure.adapter.input.web.client.PessoaWebClientAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.ms.rr.produto_service.domain.exception.BaseErrorMessage.FORNECEDOR_NOT_FOUND;
import static com.ms.rr.produto_service.domain.exception.BaseErrorMessage.PRODUTO_NOT_FOUND;

@Service
public class ProdutoService implements ProdutoUseCase {

    private final ProdutoOutputPort produtoOutputPort;
    private final PessoaWebClientAdapter pessoaWebClientAdapter;

    public ProdutoService(ProdutoOutputPort produtoOutputPort, PessoaWebClientAdapter pessoaWebClientAdapter) {
        this.produtoOutputPort = produtoOutputPort;
        this.pessoaWebClientAdapter = pessoaWebClientAdapter;
    }

    @Override
    public Mono<Void> salvar(ProdutoDomain produtoDomain) {
        return pessoaWebClientAdapter.findFornecedorById(produtoDomain.fornecedorId())
                .switchIfEmpty(Mono.defer(() -> Mono.error(
                        new FornecedorNotFoundException(FORNECEDOR_NOT_FOUND.params(produtoDomain.fornecedorId().toString())
                                .getMessage())
                )))
                .flatMap(fornecedorDTO -> produtoOutputPort.save(produtoDomain));
    }

    @Override
    public ProdutoDomain buscarPorId(Long id) {
        var produto = produtoOutputPort.findById(id);
        if (produto != null) {
            return produto;
        }
        throw new ProdutoNotFoundException(PRODUTO_NOT_FOUND.params(id.toString()).getMessage());
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
        if (produtoOutputPort.findById(id) != null) {
            produtoOutputPort.delete(id);
        }
        throw new ProdutoNotFoundException(PRODUTO_NOT_FOUND.params(id.toString()).getMessage());
    }
}
