package com.ms.rr.produto_service.infrastructure.adapter.input.web.rest.impl;

import com.ms.rr.produto_service.application.api.ProdutoApi;
import com.ms.rr.produto_service.application.dto.in.CreateProduto;
import com.ms.rr.produto_service.application.dto.in.UpdateProduto;
import com.ms.rr.produto_service.application.dto.out.ProdutoResponse;
import com.ms.rr.produto_service.infrastructure.adapter.input.web.rest.ProdutoResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/produto")
public class ProdutoResourceImpl implements ProdutoResource {

    private static final Logger log = LoggerFactory.getLogger(ProdutoResourceImpl.class);

    private final ProdutoApi produtoApi;

    public ProdutoResourceImpl(ProdutoApi produtoApi) {
        this.produtoApi = produtoApi;
    }


    @Override
    public Mono<ResponseEntity<Object>> createProduto(CreateProduto createProduto) {
        log.info("Criando produto com fornecedor válido: {}", createProduto.toString());

        return produtoApi.saveProduto(createProduto)
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()))
                .onErrorResume(e -> {
                    log.error("Erro ao criar produto: {}", e.getMessage(), e);
                    return Mono.just(ResponseEntity.badRequest().build());
                });
    }

    @Override
    public ResponseEntity<String> create(CreateProduto createProduto) {
        log.info("Criando produto: {}", createProduto);
        produtoApi.create(createProduto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Produto criado com sucesso");
    }

    @Override
    public ResponseEntity<ProdutoResponse> findById(Long id) {
        log.info("Buscando produto por id: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(produtoApi.findById(id));
    }

    @Override
    public ResponseEntity<Page<ProdutoResponse>> findAll(Integer page, Integer size) {
        log.info("Buscando produtos");
        Page<ProdutoResponse> produtos = produtoApi.list(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @Override
    public ResponseEntity<Page<ProdutoResponse>> findAllByCategoria(String categoria, Integer page, Integer size) {
        log.info("Buscando produtos com nome: {}", categoria);
        Page<ProdutoResponse> produtos = produtoApi.listByCategoria(categoria, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @Override
    public ResponseEntity<ProdutoResponse> update(Long id, UpdateProduto updateProduto) {
        log.info("Atualizando produto id: {}", id);
        var produto = produtoApi.update(id, updateProduto);
        return ResponseEntity.status(HttpStatus.OK).body(produto);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        log.info("Deletando produto id: {}", id);
        produtoApi.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
