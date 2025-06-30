package com.ms.rr.produto_service.application.port.input;

import com.ms.rr.produto_service.application.dto.out.ProdutoResponse;
import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;


public interface ProdutoUseCase {

    Mono<Void> salvar(ProdutoDomain produtoDomain);

    ProdutoDomain buscarPorId(Long id);

    Page<ProdutoDomain> buscarTodosProdutos(int page, int size);

    Page<ProdutoDomain> buscarProdutosPorCategoria(String categoria, int page, int size);

    void excluir(Long id);
}
