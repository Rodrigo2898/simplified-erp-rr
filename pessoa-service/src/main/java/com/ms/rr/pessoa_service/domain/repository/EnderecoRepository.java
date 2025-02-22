package com.ms.rr.pessoa_service.domain.repository;

import com.ms.rr.pessoa_service.domain.model.Endereco;

import java.util.List;

public interface EnderecoRepository extends BaseRepository<Endereco, Long> {

    List<Endereco> findByPessoaId(long pessoaId);
}
