package com.ms.rr.produto_service.application.port.input;

import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import org.springframework.data.domain.Page;


public interface ProdutoUseCase {

    void salvar(ProdutoDomain produtoDomain);

    ProdutoDomain buscarPorId(Long id);

    Page<ProdutoDomain> buscarTodosProdutos(int page, int size);

    Page<ProdutoDomain> buscarProdutosPorNome(String nome, int page, int size);

    void excluir(Long id);
}
