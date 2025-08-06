package com.ms.rr.produto_service.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.dto.in.UpdateProduto;
import com.ms.rr.produto_service.domain.dto.out.ProdutoResponse;
import com.ms.rr.produto_service.domain.model.ProdutoCriadoEvent;
import com.ms.rr.produto_service.domain.port.input.ProdutoUseCase;
import com.ms.rr.produto_service.domain.port.output.FornecedorOutputPort;
import com.ms.rr.produto_service.domain.port.output.ProdutoCriadoOutputPort;
import com.ms.rr.produto_service.domain.port.output.ProdutoOutputPort;
import com.ms.rr.produto_service.domain.exception.FornecedorNotFoundException;
import com.ms.rr.produto_service.domain.exception.ProdutoNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.ms.rr.produto_service.domain.exception.BaseErrorMessage.*;

@Service
public class ProdutoService implements ProdutoUseCase {

    private final Logger log = LoggerFactory.getLogger(ProdutoService.class);
    private final ProdutoOutputPort produtoOutputPort;
    private final FornecedorOutputPort fornecedorOutputPort;
    private final ProdutoCriadoOutputPort produtoCriadoOutputPort;

    public ProdutoService(ProdutoOutputPort produtoOutputPort, FornecedorOutputPort fornecedorOutputPort,
                          ProdutoCriadoOutputPort produtoCriadoOutputPort) {
        this.produtoOutputPort = produtoOutputPort;
        this.fornecedorOutputPort = fornecedorOutputPort;
        this.produtoCriadoOutputPort = produtoCriadoOutputPort;
    }

    @Override
    public Mono<ProdutoResponse> salvar(CreateProduto createProduto) {
        return fornecedorOutputPort.findFornecedorById(createProduto.fornecedorId())
                .switchIfEmpty(Mono.error(new FornecedorNotFoundException(
                        FORNECEDOR_NOT_FOUND.params(createProduto.fornecedorId().toString()).getMessage())))
                .flatMap(fornecedorDTO ->
                        produtoOutputPort.save(createProduto.toDomain())
                                .flatMap(saved -> produtoCriadoOutputPort.sendMessage(ProdutoCriadoEvent.fromDomain(saved))
                                        .thenReturn(saved))
                )
                .map(ProdutoResponse::fromDomain)
                .doOnSuccess(produto -> log.info("Produto Salvo com sucesso"))
                .doOnError(e -> log.error("Erro ao salvar produto", e));
    }

    @Override
    public Mono<ProdutoResponse> buscarPorId(Long id) {
        return produtoOutputPort.findById(id)
                .switchIfEmpty(Mono.error(new ProdutoNotFoundException(
                        PRODUTO_NOT_FOUND.params(id.toString()).getMessage()
                )))
                .map(ProdutoResponse::fromDomain)
                .doOnSuccess(produto -> log.info("Produto encontrado: {}", id))
                .doOnError(e -> log.error("Erro ao buscar produto", e));
    }

    @Override
    public Flux<ProdutoResponse> buscarTodosProdutos() {
        return produtoOutputPort.findAll()
                .map(ProdutoResponse::fromDomain);
    }

    @Override
    public Flux<ProdutoResponse> buscarProdutosPorCategoria(String categoria) {
        return produtoOutputPort.findAllByCategoria(categoria)
                .map(ProdutoResponse::fromDomain);
    }

    /**
     * Todo
     * Otimizar metodo
     * Melhorar a atualização de produtos com os fornecedores vinculados
     * */
    @Override
    public Mono<ProdutoResponse> atualizar(Long id, UpdateProduto updateProduto) {
        return produtoOutputPort.findById(id)
                .switchIfEmpty(Mono.error(new ProdutoNotFoundException(
                        PRODUTO_NOT_FOUND.params(id.toString()).getMessage())))
                .flatMap(produto -> produtoOutputPort.save(updateProduto.toDomain(produto.id())))
                .map(ProdutoResponse::fromDomain)
                .doOnSuccess(produto -> log.info("Produto atualizado: {}", id))
                .doOnError(e -> log.error("Erro ao atualizar produto", e));
    }

    @Override
    public Mono<Void> excluir(Long id) {
        return produtoOutputPort.findById(id)
                .switchIfEmpty(Mono.error(new ProdutoNotFoundException(
                        PRODUTO_NOT_FOUND.params(id.toString()).getMessage())))
                .flatMap(produto -> produtoOutputPort.delete(id))
                .doOnError(e -> log.error("Erro ao excluir produto", e));
    }
}
