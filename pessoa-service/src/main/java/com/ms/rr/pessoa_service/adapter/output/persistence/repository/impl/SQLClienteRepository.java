package com.ms.rr.pessoa_service.adapter.output.persistence.repository.impl;

import com.ms.rr.pessoa_service.domain.port.output.ClienteOutputPort;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.domain.query.ClienteQuery;
import com.ms.rr.pessoa_service.adapter.output.persistence.entity.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<ClienteDomain> find(ClienteQuery clienteQuery) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        List<Predicate> predicates = new ArrayList<>();

        clienteQuery.ids().ifPresent(ids -> predicates.add(root.get("id").in(ids)));

        clienteQuery.nome().ifPresent(nome -> predicates.add(
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nome")),
                        nome.toLowerCase() + "%")));

        clienteQuery.cpf().ifPresent(cpf -> predicates.add(criteriaBuilder.equal(root.get("cpf"), cpf)));

        if (!predicates.isEmpty()) {
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }

        TypedQuery<Cliente> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList().stream()
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
