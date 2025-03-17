package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.repository;

import com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
