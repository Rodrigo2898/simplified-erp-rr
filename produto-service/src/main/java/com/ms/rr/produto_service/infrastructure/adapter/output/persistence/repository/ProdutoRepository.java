package com.ms.rr.produto_service.infrastructure.adapter.output.persistence.repository;

import com.ms.rr.produto_service.infrastructure.adapter.output.persistence.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
