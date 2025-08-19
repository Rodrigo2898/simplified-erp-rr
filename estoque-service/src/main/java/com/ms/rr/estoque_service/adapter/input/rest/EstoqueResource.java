package com.ms.rr.estoque_service.adapter.input.rest;

import com.ms.rr.estoque_service.domain.dto.out.ApiResponse;
import com.ms.rr.estoque_service.domain.dto.out.EstoqueResponse;
import com.ms.rr.estoque_service.domain.dto.out.PaginationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface EstoqueResource {

    @GetMapping("/tipo-produto/{name}")
    ResponseEntity<ApiResponse<EstoqueResponse>> findAllByTipo(@PathVariable("name")
                                                                  String tipo,
                                                               @RequestParam(name = "page", defaultValue = "0")
                                                                  Integer page,
                                                               @RequestParam(name = "pageSize", defaultValue = "10")
                                                                  Integer pageSize);
}
