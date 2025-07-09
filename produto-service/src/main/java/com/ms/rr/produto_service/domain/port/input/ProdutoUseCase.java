package com.ms.rr.produto_service.domain.port.input;

import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.dto.in.UpdateProduto;
import com.ms.rr.produto_service.domain.dto.out.ProdutoResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ProdutoUseCase {

    Mono<Void> salvar(CreateProduto createProduto);

    Mono<ProdutoResponse> buscarPorId(Long id);

    Flux<ProdutoResponse> buscarTodosProdutos();

    Flux<ProdutoResponse> buscarProdutosPorCategoria(String categoria);

    Mono<ProdutoResponse> atualizar(Long id, UpdateProduto updateProduto);

    Mono<Void> excluir(Long id);
}
