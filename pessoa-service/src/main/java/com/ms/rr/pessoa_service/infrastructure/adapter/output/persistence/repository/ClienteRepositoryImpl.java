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
    public Optional<ClienteDomain> findById(Long id) {
        var cliente = entityManager.find(Cliente.class, id);
        return Optional.ofNullable(cliente)
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

    @Override
    public void delete(ClienteDomain domain) {
        var entity = entityManager.find(Cliente.class, domain.getId());
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
