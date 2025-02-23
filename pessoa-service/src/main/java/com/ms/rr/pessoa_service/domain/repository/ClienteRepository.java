package com.ms.rr.pessoa_service.domain.repository;

import com.ms.rr.pessoa_service.domain.model.Cliente;

public interface ClienteRepository extends BaseRepository<Cliente, Long> {

    Cliente findClienteByCpf(String email);
}
