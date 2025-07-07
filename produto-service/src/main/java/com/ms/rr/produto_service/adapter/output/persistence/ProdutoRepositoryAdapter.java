package com.ms.rr.produto_service.adapter.output.persistence;

import com.ms.rr.produto_service.adapter.output.persistence.repository.ProdutoRepository;
import com.ms.rr.produto_service.domain.port.output.ProdutoOutputPort;
import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import com.ms.rr.produto_service.adapter.output.persistence.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ProdutoRepositoryAdapter implements ProdutoOutputPort {

    private final ProdutoRepository produtoRepository;

    public ProdutoRepositoryAdapter(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Mono<Void> save(ProdutoDomain produto) {
        produtoRepository.save(Produto.fromDomain(produto));
        return Mono.empty();
    }

    @Override
    public ProdutoDomain findById(Long id) {
        return produtoRepository.findById(id)
                .map(Produto::toDomain).orElseThrow();
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
