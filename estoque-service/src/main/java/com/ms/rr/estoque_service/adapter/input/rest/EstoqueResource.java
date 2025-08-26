package com.ms.rr.estoque_service.adapter.input.rest;

import com.ms.rr.estoque_service.domain.dto.out.ApiResponse;
import com.ms.rr.estoque_service.domain.dto.out.EstoqueResponse;
import com.ms.rr.estoque_service.domain.dto.out.PaginationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface EstoqueResource {

    @GetMapping("/{nomeProduto}")
    ResponseEntity<EstoqueResponse> findByNome(@PathVariable("nomeProduto") String nomeProduto);

    @GetMapping
    ResponseEntity<ApiResponse<EstoqueResponse>> findAll(@RequestParam(name = "page", defaultValue = "0")
                                                         Integer page,
                                                         @RequestParam(name = "pageSize", defaultValue = "10")
                                                         Integer pageSize);

    @GetMapping("/tipo-produto/{tipo}")
    ResponseEntity<ApiResponse<EstoqueResponse>> findAllByTipo(@PathVariable("tipo")
                                                               String tipo,
                                                               @RequestParam(name = "page", defaultValue = "0")
                                                               Integer page,
                                                               @RequestParam(name = "pageSize", defaultValue = "10")
                                                               Integer pageSize);

    @DeleteMapping("/nome-produto/{nomeProduto}")
    ResponseEntity<String> deleteByName(@PathVariable("nomeProduto")
                                        String nomeProduto,
                                        @RequestParam(name = "quantidade", defaultValue = "1")
                                        Integer quantidade);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable("id")
                                        String id);
}
