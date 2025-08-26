package com.ms.rr.estoque_service.adapter.output.persistence.repository;

import com.ms.rr.estoque_service.adapter.output.persistence.document.Estoque;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EstoqueRepository extends MongoRepository<Estoque, String> {

    Optional<Estoque> findByNomeProduto(String nomeProduto);

    Page<Estoque> findAllByTipoProduto(String tipoProduto, PageRequest pageRequest);
}
