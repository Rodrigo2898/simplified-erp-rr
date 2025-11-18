package com.ms.rr.pessoa_service.domain.port.output;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseOutputPort<Entity, ID extends Serializable> {

    void save(List<Entity> entities);

    default void save(Entity entity) {
        save(List.of(entity));
    }

    void delete(Entity entity);
}
