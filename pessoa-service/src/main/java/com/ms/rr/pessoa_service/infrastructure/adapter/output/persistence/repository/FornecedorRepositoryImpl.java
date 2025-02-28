package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.repository;

import com.ms.rr.pessoa_service.application.port.output.FornecedorOutputPort;
import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;
import com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity.Fornecedor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FornecedorRepositoryImpl implements FornecedorOutputPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(FornecedorDomain fornecedorDomain) {
        entityManager.persist(Fornecedor.fromDomain(fornecedorDomain));
    }

    @Override
    public Optional<FornecedorDomain> findById(Long id) {
        var fornecedor = entityManager.find(Fornecedor.class, id);
        return Optional.ofNullable(fornecedor)
                .map(Fornecedor::toDomain);
    }

    @Override
    public FornecedorDomain findFornecedorByCnpj(String cnpj) {
        return null;
    }

    @Override
    public List<FornecedorDomain> findAll() {
        List<Fornecedor> fornecedores = entityManager
                .createQuery("SELECT f FROM Fornecedor f", Fornecedor.class)
                .getResultList();
        return fornecedores.stream()
                .map(Fornecedor::toDomain)
                .toList();
    }

    @Override
    public void delete(FornecedorDomain domain) {
        var entity = entityManager.find(Fornecedor.class, domain.getId());
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
