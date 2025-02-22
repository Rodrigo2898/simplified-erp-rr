package com.ms.rr.pessoa_service.domain.repository;

import com.ms.rr.pessoa_service.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends BaseRepository<Cliente, Long> {
    Cliente findClienteByCpf(String email);
}
