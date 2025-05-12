package com.ms.rr.produto_service.application.api;

import org.springframework.data.domain.Page;

import java.util.List;

public interface BaseApi<CreateDTO, ResponseDTO, UpdateDTO, ID> {

    void create(CreateDTO createDTO);

    Page<ResponseDTO> list(int page, int size);

    Page<ResponseDTO> listByNome(String nome, int page, int size);

    ResponseDTO findById(ID id);

    ResponseDTO update(ID id, UpdateDTO updateDTO);

    void delete(ID id);
}
