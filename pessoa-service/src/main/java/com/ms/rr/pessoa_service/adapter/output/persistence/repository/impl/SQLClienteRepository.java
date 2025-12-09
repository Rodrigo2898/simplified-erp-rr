package com.ms.rr.pessoa_service.adapter.output.persistence.repository.impl;

import com.ms.rr.pessoa_service.domain.port.output.ClienteOutputPort;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.adapter.output.persistence.entity.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class SQLClienteRepository implements ClienteOutputPort {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public void save(ClienteDomain clienteDomain) {
        entityManager.persist(Cliente.fromDomain(clienteDomain));
    }

    @Override
    public Optional<ClienteDomain> findById(Long id) {
        Cliente entity = entityManager.find(Cliente.class, id);
        return Optional.ofNullable(entity)
                .map(Cliente::toDomain);
    }

    @Override
    public ClienteDomain findClienteByCpf(String cpf) {
        return null;
    }

    @Override
    public List<ClienteDomain> findAll() {
        List<Cliente> clientes = entityManager
                .createQuery("SELECT c FROM Cliente c", Cliente.class)
                .getResultList();
        return clientes.stream()
                .map(Cliente::toDomain)
                .toList();
    }

    @Transactional
    @Override
    public void delete(ClienteDomain domain) {
        var entity = entityManager.find(Cliente.class, domain.id());
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
