package com.ms.rr.pessoa_service.adapter.output.persistence.repository.impl;

import com.ms.rr.pessoa_service.domain.port.output.ClienteOutputPort;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.adapter.output.persistence.entity.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("cpf"), cpf));

        return entityManager
                .createQuery(criteriaQuery)
                .getSingleResult().toDomain();
    }

    @Override
    public void update(Long id, ClienteDomain clienteDomain) {
        StringBuilder jpql = new StringBuilder("UPDATE Cliente c SET ");
        Map<String, Object> params = new HashMap<>();

        if (clienteDomain.nome() != null) {
            jpql.append("c.nome = :nome, ");
            params.put("nome", clienteDomain.nome());
        }

        if (clienteDomain.email() != null) {
            jpql.append("c.email = :email, ");
            params.put("email", clienteDomain.email());
        }

        if (clienteDomain.telefone() != null) {
            jpql.append("c.telefone = :telefone, ");
            params.put("telefone", clienteDomain.telefone());
        }

        if (clienteDomain.endereco().nomeRua() != null) {
            jpql.append("c.endereco.nomeRua = :nomeRua , ");
            params.put("nomeRua", clienteDomain.endereco().nomeRua());
        }

        if (clienteDomain.endereco().numeroRua() != null) {
            jpql.append("c.endereco.numeroRua = :numeroRua , ");
            params.put("numeroRua", clienteDomain.endereco().numeroRua());
        }

        if (clienteDomain.endereco().bairro() != null) {
            jpql.append("c.endereco.bairro = :bairro , ");
            params.put("bairro", clienteDomain.endereco().bairro());
        }

        if (clienteDomain.endereco().cidade() != null) {
            jpql.append("c.endereco.cidade = :cidade , ");
            params.put("cidade", clienteDomain.endereco().cidade());
        }

        if (clienteDomain.endereco().estado() != null) {
            jpql.append("c.endereco.estado = :estado , ");
            params.put("estado", clienteDomain.endereco().estado());
        }

        jpql.setLength(jpql.length() - 2);

        jpql.append(" WHERE f.id = :id");

        Query query = entityManager.createQuery(jpql.toString());
        params.forEach(query::setParameter);
        query.setParameter("id", id);

        query.executeUpdate();
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
