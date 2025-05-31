package com.ms.rr.produto_service.application.api.impl;

import com.ms.rr.produto_service.application.api.ProdutoApi;
import com.ms.rr.produto_service.application.dto.in.CreateProduto;
import com.ms.rr.produto_service.application.dto.in.UpdateProduto;
import com.ms.rr.produto_service.application.dto.out.ProdutoResponse;
import com.ms.rr.produto_service.application.port.input.ProdutoUseCase;
import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ProdutoApiImpl implements ProdutoApi {

    private final ProdutoUseCase produtoUseCase;

    public ProdutoApiImpl(ProdutoUseCase produtoUseCase) {
        this.produtoUseCase = produtoUseCase;
    }

    @Override
    public void create(CreateProduto createProduto) {
        produtoUseCase.salvar(createProduto.toDomain());
    }

    @Override
    public ProdutoResponse findById(Long id) {
        return ProdutoResponse.fromDomain(produtoUseCase.buscarPorId(id));
    }


    @Override
    public Page<ProdutoResponse> list(int page, int size) {
        return produtoUseCase.buscarTodosProdutos(page, size)
                .map(ProdutoResponse::fromDomain);
    }

    @Override
    public Page<ProdutoResponse> listByCategoria(String categoria, int page, int size) {
        return produtoUseCase.buscarProdutosPorCategoria(categoria, page, size)
                .map(ProdutoResponse::fromDomain);
    }


    /**
     * Todo
     * Otimizar metodo
     * */
    @Override
    public ProdutoResponse update(Long id, UpdateProduto updateProduto) {
        ProdutoDomain produtoExistente = produtoUseCase.buscarPorId(id);
        produtoUseCase.salvar(updateProduto.toDomain(produtoExistente.id()));
        return ProdutoResponse.fromDomain(produtoUseCase.buscarPorId(id));
    }

    @Override
    public void delete(Long id) {
        produtoUseCase.excluir(id);
    }
}
