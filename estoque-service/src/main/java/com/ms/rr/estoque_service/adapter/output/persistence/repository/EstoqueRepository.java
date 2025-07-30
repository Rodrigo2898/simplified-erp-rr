package com.ms.rr.estoque_service.adapter.output.persistence.repository;

import com.ms.rr.estoque_service.adapter.output.persistence.document.Estoque;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EstoqueRepository extends MongoRepository<Estoque, Long> {
}
