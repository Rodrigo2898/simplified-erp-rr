package com.ms.rr.produto_service.application.port.input;

import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProdutoUseCase {

    void salvar(ProdutoDomain produtoDomain);

    Page<ProdutoDomain> buscarPorNome(String nome, Pageable pageable);

    Page<ProdutoDomain> buscarTodos(Pageable pageable);

    void excluir(Long id);
}
