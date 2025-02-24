package com.ms.rr.pessoa_service.domain.repository;

import com.ms.rr.pessoa_service.domain.model.Pessoa;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface PessoaRepository<Entity extends Pessoa, ID extends Serializable> {

    void save(Entity entity);
    Optional<Entity> findById(ID id);
    List<Entity> findAll();
    void deleteById(ID id);
}
