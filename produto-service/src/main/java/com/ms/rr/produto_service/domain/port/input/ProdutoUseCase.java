package com.ms.rr.produto_service.domain.port.input;

import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.dto.in.UpdateProduto;
import com.ms.rr.produto_service.domain.dto.out.ProdutoResponse;
import org.springframework.data.domain.Page;;
import reactor.core.publisher.Mono;


public interface ProdutoUseCase {

    Mono<Void> salvar(CreateProduto createProduto);

    Mono<ProdutoResponse> buscarPorId(Long id);

    Mono<Page<ProdutoResponse>> buscarTodosProdutos(int page, int size);

    Mono<Page<ProdutoResponse>> buscarProdutosPorCategoria(String categoria, int page, int size);

    Mono<ProdutoResponse> atualizar(Long id, UpdateProduto updateProduto);

    Mono<Void> excluir(Long id);
}
