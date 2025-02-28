package com.ms.rr.pessoa_service.application.port.output;

import com.ms.rr.pessoa_service.domain.model.PessoaDomain;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface PessoaOutputPort<Entity extends PessoaDomain, ID extends Serializable> {

    void save(Entity entity);

    Optional<Entity> findById(ID id);

    List<Entity> findAll();

    void delete(Entity entity);

    default void deleteById(ID id) {
        Entity entity = findById(id).orElseThrow();
        delete(entity);
    }
}
