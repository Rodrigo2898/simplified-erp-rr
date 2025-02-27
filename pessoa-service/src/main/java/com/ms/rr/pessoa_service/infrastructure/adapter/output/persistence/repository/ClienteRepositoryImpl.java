package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.repository;

import com.ms.rr.pessoa_service.application.port.output.ClienteOutputPort;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepositoryImpl implements ClienteOutputPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(ClienteDomain clienteDomain) {
        entityManager.persist(Cliente.fromDomain(clienteDomain));
    }

    @Override
    public Optional<ClienteDomain> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public ClienteDomain findClienteByCpf(String cpf) {
        return null;
    }

    @Override
    public List<ClienteDomain> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
