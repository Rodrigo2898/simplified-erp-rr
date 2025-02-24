package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.repository;

import com.ms.rr.pessoa_service.domain.model.Cliente;
import com.ms.rr.pessoa_service.domain.repository.ClienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ImplClienteRepository implements ClienteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Cliente findClienteByCpf(String email) {
        return null;
    }

    @Override
    public void save(Cliente cliente) {

    }

    @Override
    public Optional<Cliente> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Cliente> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
