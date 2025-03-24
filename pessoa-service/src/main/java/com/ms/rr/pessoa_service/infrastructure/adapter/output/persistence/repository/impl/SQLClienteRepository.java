package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.repository.impl;

import com.ms.rr.pessoa_service.application.port.output.ClienteOutputPort;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity.Cliente;
import com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.repository.ClienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

//@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Repository
public class SQLClienteRepository implements ClienteOutputPort {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public void save(List<ClienteDomain> clientes) {
        clientes.stream()
                .map(Cliente::fromDomain)
                .forEach(cliente -> {
                    entityManager.persist(cliente);
                    System.out.println(cliente.toString());
                });
        entityManager.flush();
        entityManager.clear();
    }

    @Override
    public Optional<ClienteDomain> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        Cliente cliente = entityManager.find(Cliente.class, id);
        return Optional.ofNullable(cliente).map(Cliente::toDomain);
    }

    @Override
    public ClienteDomain findClienteByCpf(String cpf) {
        return null;
    }

    @Override
    public List<ClienteDomain> findAll() {
        return null;
    }

    @Transactional
    @Override
    public void delete(ClienteDomain domain) {
        var entity = entityManager.find(Cliente.class, domain.getId());
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
