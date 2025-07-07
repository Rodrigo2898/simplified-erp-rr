package com.ms.rr.produto_service.adapter.input.rest.impl;

import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.dto.in.UpdateProduto;
import com.ms.rr.produto_service.domain.dto.out.ProdutoResponse;
import com.ms.rr.produto_service.domain.port.input.ProdutoUseCase;
import com.ms.rr.produto_service.adapter.input.rest.ProdutoResource;
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

    private final ProdutoUseCase produtoUseCase;

    public ProdutoResourceImpl(ProdutoUseCase produtoUseCase) {
        this.produtoUseCase = produtoUseCase;
    }

    @Override
    public Mono<ResponseEntity<String>> create(CreateProduto createProduto) {
        log.info("Criando produto com fornecedor válido: {}", createProduto.toString());
        return produtoUseCase.salvar(createProduto)
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).body("Produto com fornecedor criado com sucesso")))
                .onErrorResume(e -> {
                    log.error("Erro ao criar produto: {}", e.getMessage(), e);
                    return Mono.just(ResponseEntity.badRequest().build());
                });
    }

    @Override
    public ResponseEntity<ProdutoResponse> findById(Long id) {
        log.info("Buscando produto por id: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(produtoUseCase.buscarPorId(id));
    }

    @Override
    public ResponseEntity<Page<ProdutoResponse>> findAll(Integer page, Integer size) {
        log.info("Buscando produtos");
        Page<ProdutoResponse> produtos = produtoUseCase.buscarTodosProdutos(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @Override
    public ResponseEntity<Page<ProdutoResponse>> findAllByCategoria(String categoria, Integer page, Integer size) {
        log.info("Buscando produtos com nome: {}", categoria);
        Page<ProdutoResponse> produtos = produtoUseCase.buscarProdutosPorCategoria(categoria, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @Override
    public ResponseEntity<ProdutoResponse> update(Long id, UpdateProduto updateProduto) {
        log.info("Atualizando produto id: {}", id);
        var produto = produtoUseCase.atualizar(id, updateProduto);
        return ResponseEntity.status(HttpStatus.OK).body(produto);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        log.info("Deletando produto id: {}", id);
        produtoUseCase.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
