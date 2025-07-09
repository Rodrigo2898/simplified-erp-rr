package com.ms.rr.produto_service.adapter.output.persistence;

import com.ms.rr.produto_service.adapter.output.persistence.repository.ProdutoRepository;
import com.ms.rr.produto_service.domain.port.output.ProdutoOutputPort;
import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import com.ms.rr.produto_service.adapter.output.persistence.document.Produto;
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
    public Flux<ProdutoDomain> findAll() {
        return produtoRepository.findAll()
                .map(Produto::toDomain);
    }

    @Override
    public Flux<ProdutoDomain> findAllByCategoria(String categoria) {
        return produtoRepository.findAllByCategoria(categoria)
                .map(Produto::toDomain);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return produtoRepository.deleteById(id);
    }
}
