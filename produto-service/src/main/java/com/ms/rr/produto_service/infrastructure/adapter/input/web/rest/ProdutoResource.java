package com.ms.rr.produto_service.infrastructure.adapter.input.web.rest;

import com.ms.rr.produto_service.application.dto.in.CreateProduto;
import com.ms.rr.produto_service.application.dto.in.UpdateProduto;
import com.ms.rr.produto_service.application.dto.out.ProdutoResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface ProdutoResource {

    @PostMapping
    ResponseEntity<String> create(@RequestBody CreateProduto createProduto);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProdutoResponse> findById(@PathVariable("id") Long id);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ProdutoResponse>> findAll(@RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer size);

    @GetMapping(value = "/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ProdutoResponse>> findAllByNomeContaining(@PathVariable("nome") String nome,
                                                                  @RequestParam(defaultValue = "0") Integer page,
                                                                  @RequestParam(defaultValue = "10") Integer size);

    @PutMapping(value = "/{id}")
    ResponseEntity<ProdutoResponse> update(@PathVariable("id") Long id, @RequestBody UpdateProduto updateProduto);

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);
}
