package com.ms.rr.produto_service.domain.port.input;

import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.dto.in.UpdateProduto;
import com.ms.rr.produto_service.domain.dto.out.ProdutoResponse;
import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;


public interface ProdutoUseCase {

    Mono<Void> salvar(CreateProduto createProduto);

    ProdutoResponse buscarPorId(Long id);

    Page<ProdutoResponse> buscarTodosProdutos(int page, int size);

    Page<ProdutoResponse> buscarProdutosPorCategoria(String categoria, int page, int size);

    ProdutoResponse atualizar(Long id, UpdateProduto updateProduto);

    void excluir(Long id);
}
