package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.repository;

import com.ms.rr.pessoa_service.domain.model.Fornecedor;
import com.ms.rr.pessoa_service.domain.repository.FornecedorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ImplFornecedorRepository implements FornecedorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Fornecedor findFornecedorByCnpj(String cnpj) {
        return null;
    }

    @Override
    public void save(Fornecedor fornecedor) {
        entityManager.persist(fornecedor);
    }

    @Override
    public Optional<Fornecedor> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Fornecedor> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
