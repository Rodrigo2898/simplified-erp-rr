package com.ms.rr.pessoa_service.domain.repository;

import com.ms.rr.pessoa_service.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<Entity, ID> {

    void save(Entity entity);
    Optional<Entity> findById(ID id);
    List<Entity> findAll();
}
