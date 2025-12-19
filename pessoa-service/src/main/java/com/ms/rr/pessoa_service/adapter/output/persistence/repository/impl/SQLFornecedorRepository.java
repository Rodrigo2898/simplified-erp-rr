package com.ms.rr.pessoa_service.adapter.output.persistence.repository.impl;

import com.ms.rr.pessoa_service.adapter.output.persistence.entity.vo.Endereco;
import com.ms.rr.pessoa_service.domain.port.output.FornecedorOutputPort;
import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;
import com.ms.rr.pessoa_service.adapter.output.persistence.entity.Fornecedor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public class SQLFornecedorRepository implements FornecedorOutputPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Fornecedor> criteriaQuery = criteriaBuilder.createQuery(Fornecedor.class);
        Root<Fornecedor> root = criteriaQuery.from(Fornecedor.class);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("cnpj"), cnpj));

        return entityManager
                .createQuery(criteriaQuery)
                .getSingleResult().toDomain();
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
        var entity = entityManager.find(Fornecedor.class, domain.id());
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    /*
    * TODO
    *  Deixar o método mais performático
    * */

    @Override
    public void update(Long id, FornecedorDomain fornecedorDomain) {
        StringBuilder jpql = new StringBuilder("UPDATE Fornecedor f SET ");
        Map<String, Object> params = new HashMap<>();

        if (fornecedorDomain.nome() != null) {
            jpql.append("f.nome = :nome, ");
            params.put("nome", fornecedorDomain.nome());
        }

        if (fornecedorDomain.email() != null) {
            jpql.append("f.email = :email, ");
            params.put("email", fornecedorDomain.email());
        }

        if (fornecedorDomain.telefone() != null) {
            jpql.append("f.telefone = :telefone, ");
            params.put("telefone", fornecedorDomain.telefone());
        }

        if (fornecedorDomain.endereco().nomeRua() != null) {
            jpql.append("f.endereco.nomeRua = :nomeRua , ");
            params.put("nomeRua", fornecedorDomain.endereco().nomeRua());
        }

        if (fornecedorDomain.endereco().numeroRua() != null) {
            jpql.append("f.endereco.numeroRua = :numeroRua , ");
            params.put("numeroRua", fornecedorDomain.endereco().numeroRua());
        }

        if (fornecedorDomain.endereco().bairro() != null) {
            jpql.append("f.endereco.bairro = :bairro , ");
            params.put("bairro", fornecedorDomain.endereco().bairro());
        }

        if (fornecedorDomain.endereco().cidade() != null) {
            jpql.append("f.endereco.cidade = :cidade , ");
        }

        if (fornecedorDomain.endereco().estado() != null) {
            jpql.append("f.endereco.estado = :estado , ");
        }

        jpql.setLength(jpql.length() - 2);

        jpql.append(" WHERE f.id = :id");

        Query query = entityManager.createQuery(jpql.toString());
        params.forEach(query::setParameter);
        query.setParameter("id", id);

        query.executeUpdate();
    }
}
