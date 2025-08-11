package com.ms.rr.estoque_service.adapter.output.persistence.repository;

import com.ms.rr.estoque_service.adapter.output.persistence.document.Estoque;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EstoqueRepository extends MongoRepository<Estoque, Long> {

    Optional<Estoque> findByNomeProduto(String nomeProduto);
}
