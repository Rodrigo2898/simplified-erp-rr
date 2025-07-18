package com.ms.rr.produto_service.adapter.input.rest.impl;

import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.dto.in.UpdateProduto;
import com.ms.rr.produto_service.domain.dto.out.ProdutoResponse;
import com.ms.rr.produto_service.domain.port.input.ProdutoUseCase;
import com.ms.rr.produto_service.adapter.input.rest.ProdutoResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/produto")
public class ProdutoResourceImpl implements ProdutoResource {

    private static final Logger log = LoggerFactory.getLogger(ProdutoResourceImpl.class);

    private final ProdutoUseCase produtoUseCase;

    public ProdutoResourceImpl(ProdutoUseCase produtoUseCase) {
        this.produtoUseCase = produtoUseCase;
    }

    @Override
    public Mono<ProdutoResponse> create(CreateProduto createProduto) {
        return produtoUseCase.salvar(createProduto)
                .doFirst(() -> log.info("Criando produto com fornecedor válido: {}", createProduto.toString()));
    }

    @Override
    public Mono<ProdutoResponse> findById(Long id) {
        return produtoUseCase.buscarPorId(id)
                .doFirst(() -> log.info("Buscando produto por id: {}", id));
    }

    @Override
    public Flux<ProdutoResponse> findAll() {
        return produtoUseCase.buscarTodosProdutos()
                .doFirst(() -> log.info("Buscando produtos"));
    }

    @Override
    public Flux<ProdutoResponse> findAllByCategoria(String categoria) {
        return produtoUseCase.buscarProdutosPorCategoria(categoria)
                .doFirst(() -> log.info("Buscando produtos com nome: {}", categoria));
    }

    @Override
    public Mono<ProdutoResponse> update(Long id, UpdateProduto updateProduto) {
        return produtoUseCase.atualizar(id, updateProduto)
                .doFirst(() -> log.info("Atualizando produto id: {}", id));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return produtoUseCase.excluir(id)
                .doFirst(() -> log.info("Deletando produto id: {}", id));
    }
}
