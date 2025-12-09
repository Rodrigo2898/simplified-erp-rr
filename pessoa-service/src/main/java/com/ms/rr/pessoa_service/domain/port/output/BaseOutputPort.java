package com.ms.rr.pessoa_service.domain.port.output;

import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseOutputPort<Entity, ID extends Serializable> {

    void save(Entity entity);

    List<Entity> findAll();

    Optional<Entity> findById(ID id);

    void delete(Entity entity);
}
