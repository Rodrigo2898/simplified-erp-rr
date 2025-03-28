package com.ms.rr.pessoa_service.application.port.output;

import com.ms.rr.pessoa_service.domain.model.PessoaDomain;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface PessoaOutputPort<Entity, ID extends Serializable> {

    void save(List<Entity> entities);

    default void save(Entity entity) {
        save(List.of(entity));
    }

    Optional<Entity> findById(ID id);

    List<Entity> findAll();

    void delete(Entity entity);

    default void deleteById(ID id) {
        Entity entity = findById(id).orElseThrow();
        delete(entity);
    }
}
