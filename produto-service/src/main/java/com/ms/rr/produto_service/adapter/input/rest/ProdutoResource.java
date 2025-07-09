package com.ms.rr.produto_service.adapter.input.rest;

import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.dto.in.UpdateProduto;
import com.ms.rr.produto_service.domain.dto.out.ProdutoResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProdutoResource {

    @PostMapping
    Mono<ResponseEntity<String>> create(@Valid @RequestBody CreateProduto createProduto);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<ResponseEntity<ProdutoResponse>> findById(@PathVariable("id") Long id);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    Flux<ResponseEntity<ProdutoResponse>> findAll();

    @GetMapping(value = "/categoria-produto/{categoria}", produces = MediaType.APPLICATION_JSON_VALUE)
    Flux<ResponseEntity<ProdutoResponse> >findAllByCategoria(@PathVariable("categoria") String categoria);

    @PutMapping(value = "/{id}")
    Mono<ResponseEntity<ProdutoResponse>> update(@PathVariable("id") Long id, @RequestBody UpdateProduto updateProduto);

    @DeleteMapping(value = "/{id}")
    Mono<ResponseEntity<Void>> delete(@PathVariable("id") Long id);
}
