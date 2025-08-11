package com.ms.rr.estoque_service.adapter.output.persistence;

import com.ms.rr.estoque_service.adapter.output.persistence.document.Estoque;
import com.ms.rr.estoque_service.adapter.output.persistence.repository.EstoqueRepository;
import com.ms.rr.estoque_service.domain.model.EstoqueDomain;
import com.ms.rr.estoque_service.domain.port.output.EstoqueOutputPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EstoqueRepositoryAdapter implements EstoqueOutputPort {

    private final EstoqueRepository estoqueRepository;

    public EstoqueRepositoryAdapter(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    @Override
    public void save(EstoqueDomain estoqueDomain) {
        estoqueRepository.save(Estoque.fromDomain(estoqueDomain));
    }

    @Override
    public Optional<EstoqueDomain> findByNomeProduto(String nomeProduto) {
        return estoqueRepository.findByNomeProduto(nomeProduto)
                .map(Estoque::toDomain);
    }
}
