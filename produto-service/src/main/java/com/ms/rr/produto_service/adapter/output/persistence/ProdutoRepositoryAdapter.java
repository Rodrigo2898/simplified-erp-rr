package com.ms.rr.produto_service.adapter.output.persistence;

import com.ms.rr.produto_service.adapter.output.persistence.repository.ProdutoRepository;
import com.ms.rr.produto_service.domain.port.output.ProdutoOutputPort;
import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import com.ms.rr.produto_service.adapter.output.persistence.document.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProdutoRepositoryAdapter implements ProdutoOutputPort {

    private final ProdutoRepository produtoRepository;

    public ProdutoRepositoryAdapter(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Mono<ProdutoDomain> save(ProdutoDomain produto) {
        return produtoRepository.save(Produto.fromDomain(produto))
                .map(Produto::toDomain);

    }

    @Override
    public Mono<ProdutoDomain> findById(Long id) {
        return produtoRepository.findById(id)
                .map(Produto::toDomain);
    }

    @Override
    public Flux<ProdutoDomain> findAll(Pageable pageable) {
        return produtoRepository.findAll(pageable)
                .map(Produto::toDomain);
    }

    @Override
    public Flux<ProdutoDomain> findAllByCategoria(String categoria, Pageable pageable) {
        return produtoRepository.findAllByCategoria(categoria, pageable)
                .map(Produto::toDomain);
    }

    @Override
    public Mono<Long> count() {
        return produtoRepository.count();
    }

    @Override
    public Mono<Long> countByCategoria(String categoria) {
        return produtoRepository.countByCategoria(categoria);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return produtoRepository.deleteById(id);
    }
}
