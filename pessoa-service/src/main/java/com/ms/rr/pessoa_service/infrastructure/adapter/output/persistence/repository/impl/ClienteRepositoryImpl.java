package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.repository.impl;

import ch.qos.logback.core.net.server.Client;
import com.ms.rr.pessoa_service.application.port.output.ClienteOutputPort;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity.Cliente;
import com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity.Endereco;
import com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.repository.ClienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepositoryImpl implements ClienteOutputPort {

    private final ClienteRepository clienteRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ClienteRepositoryImpl(@Lazy ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    @Override
    public void save(ClienteDomain clienteDomain) {
        Cliente cliente = Cliente.fromDomain(clienteDomain);
        entityManager.merge(cliente);
    }

    @Override
    public Optional<ClienteDomain> findById(Long id) {
        return clienteRepository.findById(id)
                .map(Cliente::toDomain);
    }

    @Override
    public ClienteDomain findClienteByCpf(String cpf) {
        return null;
    }

    @Override
    public List<ClienteDomain> findAll() {
        return clienteRepository.findAll().stream().map(Cliente::toDomain).toList();
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
