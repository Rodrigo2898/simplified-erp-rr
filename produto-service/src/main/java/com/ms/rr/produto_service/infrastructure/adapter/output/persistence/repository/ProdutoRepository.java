package com.ms.rr.produto_service.infrastructure.adapter.output.persistence.repository;

import com.ms.rr.produto_service.infrastructure.adapter.output.persistence.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = "SELECT p FROM Produto p ORDER BY p.id")
    Page<Produto> findAll(Pageable pageable);
}
