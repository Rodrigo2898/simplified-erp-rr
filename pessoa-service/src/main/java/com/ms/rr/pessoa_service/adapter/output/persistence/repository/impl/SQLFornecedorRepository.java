package com.ms.rr.pessoa_service.adapter.output.persistence.repository.impl;

import com.ms.rr.pessoa_service.domain.port.output.FornecedorOutputPort;
import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;
import com.ms.rr.pessoa_service.adapter.output.persistence.entity.Fornecedor;
import jakarta.persistence.*;
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
        String cnpjNormalizado = cnpj.replaceAll("[^0-9]", "");
        StringBuilder jpql = new StringBuilder("SELECT f FROM Fornecedor f " +
                "WHERE REPLACE(REPLACE(REPLACE(f.cnpj, '.', ''), '/', ''), '-', '') = :cnpj");

        TypedQuery<Fornecedor> query = entityManager.createQuery(jpql.toString(), Fornecedor.class);
        query.setParameter("cnpj", cnpjNormalizado);

        List<Fornecedor> fornecedores = query
                .getResultList();

        return fornecedores.getFirst().toDomain();
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
            params.put("cidade", fornecedorDomain.endereco().cidade());
        }

        if (fornecedorDomain.endereco().estado() != null) {
            jpql.append("f.endereco.estado = :estado , ");
            params.put("estado", fornecedorDomain.endereco().estado());
        }

        jpql.setLength(jpql.length() - 2);

        jpql.append(" WHERE f.id = :id");

        Query query = entityManager.createQuery(jpql.toString());
        params.forEach(query::setParameter);
        query.setParameter("id", id);

        query.executeUpdate();
    }

    @Override
    public boolean emailExists(String email) {
        List<Integer> result = entityManager.createQuery(
                "SELECT 1 " +
                "FROM Fornecedor fornecedor " +
                "WHERE fornecedor.email = :email", Integer.class
        ).setParameter("email", email)
                .setMaxResults(1)
                .getResultList();

        return !result.isEmpty();
    }

    @Override
    public boolean cnpjExists(String cnpj) {
        List<Integer> result = entityManager.createQuery(
                        "SELECT 1 " +
                        "FROM Fornecedor fornecedor " +
                        "WHERE fornecedor.cnpj = :cnpj", Integer.class
                ).setParameter("cnpj", cnpj)
                .setMaxResults(1)
                .getResultList();

        return !result.isEmpty();
    }
}
