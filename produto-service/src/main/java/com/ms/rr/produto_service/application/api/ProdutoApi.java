package com.ms.rr.produto_service.application.api;

import com.ms.rr.produto_service.application.dto.in.CreateProduto;
import com.ms.rr.produto_service.application.dto.in.UpdateProduto;
import com.ms.rr.produto_service.application.dto.out.ProdutoResponse;
import org.springframework.data.domain.Page;

public interface ProdutoApi {

    void create(CreateProduto createDTO);

    Page<ProdutoResponse> list(int page, int size);

    Page<ProdutoResponse> listByCategoria(String categoria, int page, int size);

    ProdutoResponse findById(Long id);

    ProdutoResponse update(Long id, UpdateProduto updateDTO);

    void delete(Long id);
}
