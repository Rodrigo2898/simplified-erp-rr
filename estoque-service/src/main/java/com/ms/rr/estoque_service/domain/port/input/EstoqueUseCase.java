package com.ms.rr.estoque_service.domain.port.input;

import com.ms.rr.estoque_service.domain.dto.in.CreateEstoque;
import com.ms.rr.estoque_service.domain.dto.out.EstoqueResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

public interface EstoqueUseCase {
    void salvar(CreateEstoque estoque);

    EstoqueResponse buscarPorNome(String nome);

    Page<EstoqueResponse> buscarTodos(PageRequest pageRequest);

    Page<EstoqueResponse> buscarPorTipoProduto(String tipoProduto, PageRequest pageRequest);

    BigDecimal buscaTotalPorTipoProduto(String tipoProduto);
}
