package com.ms.rr.pessoa_service.application.api;

import java.util.List;
import java.util.Optional;

public interface BaseApi<CreateDTO, ResponseDTO, ID> {

    void create(CreateDTO createDTO);
    List<ResponseDTO> list();
    Optional<ResponseDTO> findById(ID id);
    void delete(ID id);
}
