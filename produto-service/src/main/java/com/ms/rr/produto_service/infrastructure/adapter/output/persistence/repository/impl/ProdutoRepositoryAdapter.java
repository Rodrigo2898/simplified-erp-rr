package com.ms.rr.produto_service.infrastructure.adapter.output.persistence.repository.impl;

import com.ms.rr.produto_service.application.port.output.ProdutoOutputPort;
import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import com.ms.rr.produto_service.infrastructure.adapter.output.persistence.entity.Produto;
import com.ms.rr.produto_service.infrastructure.adapter.output.persistence.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ProdutoRepositoryAdapter implements ProdutoOutputPort {

    private final ProdutoRepository produtoRepository;

    public ProdutoRepositoryAdapter(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public void save(ProdutoDomain produtoDomain) {
        produtoRepository.save(Produto.fromDomain(produtoDomain));
    }

    @Override
    public ProdutoDomain findById(Long id) {
        return produtoRepository.findById(id)
                .map(Produto::toDomain)
                .orElseThrow(() -> new EntityNotFoundException("Produto: " + id + " não encontrado"));
    }

    @Override
    public Page<ProdutoDomain> findAll(Pageable pageable) {
        return produtoRepository.findAllProductsWithPagination(pageable)
                .map(Produto::toDomain);
    }

    @Override
    public Page<ProdutoDomain> findAllByCategoria(String categoria, Pageable pageable) {
        return produtoRepository.findAllProductsByCategoria(categoria, pageable)
                .map(Produto::toDomain);
    }

    @Override
    public void delete(Long id) {
        produtoRepository.deleteById(id);
    }
}
