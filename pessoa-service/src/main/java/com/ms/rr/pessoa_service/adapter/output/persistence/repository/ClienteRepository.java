package com.ms.rr.pessoa_service.adapter.output.persistence.repository;

import com.ms.rr.pessoa_service.adapter.output.persistence.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
