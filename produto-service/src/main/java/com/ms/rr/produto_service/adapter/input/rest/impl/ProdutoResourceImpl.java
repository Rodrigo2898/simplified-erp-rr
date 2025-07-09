package com.ms.rr.produto_service.adapter.input.rest.impl;

import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.dto.in.UpdateProduto;
import com.ms.rr.produto_service.domain.dto.out.ProdutoResponse;
import com.ms.rr.produto_service.domain.port.input.ProdutoUseCase;
import com.ms.rr.produto_service.adapter.input.rest.ProdutoResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<String>> create(CreateProduto createProduto) {
        return produtoUseCase.salvar(createProduto)
                .doFirst(() -> log.info("Criando produto com fornecedor válido: {}", createProduto.toString()))
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).body("Produto com fornecedor criado com sucesso")))
                .onErrorResume(e -> {
                    log.error("Erro ao criar produto: {}", e.getMessage(), e);
                    return Mono.just(ResponseEntity.badRequest().build());
                });
    }

    @Override
    public Mono<ResponseEntity<ProdutoResponse>> findById(Long id) {
        return produtoUseCase.buscarPorId(id)
                .doFirst(() -> log.info("Buscando produto por id: {}", id))
                .map(produto -> ResponseEntity.status(HttpStatus.OK).body(produto))
                .onErrorResume(e -> {
                    log.error("Erro ao buscar produto: {}", e.getMessage(), e);
                    return Mono.just(ResponseEntity.badRequest().build());
                });
    }

    @Override
    public Flux<ResponseEntity<ProdutoResponse>> findAll() {
        log.info("Buscando produtos");
        return produtoUseCase.buscarTodosProdutos()
                .doFirst(() -> log.info("Buscando produtos"))
                .map(produtos -> ResponseEntity.status(HttpStatus.OK).body(produtos))
                .onErrorResume(e -> {
                    log.error("Erro ao buscar produtos: {}", e.getMessage(), e);
                    return Flux.just(ResponseEntity.badRequest().build());
                });
    }

    @Override
    public Flux<ResponseEntity<ProdutoResponse>> findAllByCategoria(String categoria) {
        log.info("Buscando produtos com nome: {}", categoria);
        return produtoUseCase.buscarProdutosPorCategoria(categoria)
                .doFirst(() -> log.info("Buscando produtos com nome: {}", categoria))
                .map(produtos -> ResponseEntity.status(HttpStatus.OK).body(produtos))
                .onErrorResume(e -> {
                    log.error("Erro ao buscar produtos: {}", e.getMessage(), e);
                    return Flux.just(ResponseEntity.badRequest().build());
                });
    }

    @Override
    public Mono<ResponseEntity<ProdutoResponse>> update(Long id, UpdateProduto updateProduto) {
        log.info("Atualizando produto id: {}", id);
        return produtoUseCase.atualizar(id, updateProduto)
                .doFirst(() -> log.info("Atualizando produto id: {}", id))
                .map(produtoAtualizado -> ResponseEntity.status(HttpStatus.OK).body(produtoAtualizado))
                .onErrorResume(e -> {
                    log.error("Erro ao atualizar produto: {}", e.getMessage(), e);
                    return Mono.just(ResponseEntity.badRequest().build());
                });
    }

    @Override
    public Mono<ResponseEntity<Void>> delete(Long id) {
        log.info("Deletando produto id: {}", id);
        return produtoUseCase.excluir(id)
                .doFirst(() -> log.info("Deletando produto id: {}", id))
                .then(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build()));
    }
}
