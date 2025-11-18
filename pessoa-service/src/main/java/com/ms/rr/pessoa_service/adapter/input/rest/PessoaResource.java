package com.ms.rr.pessoa_service.adapter.input.rest;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface PessoaResource<CreateDTO, ResponseDTO, UpdateDTO, ID> {

    @PostMapping
    ResponseEntity<String> create(@RequestBody @Valid CreateDTO dto);

    @GetMapping
    ResponseEntity<List<ResponseDTO>> getAll();

    @GetMapping("/{id}")
    ResponseEntity<ResponseDTO> getById(@PathVariable ID id);

    @PutMapping("/{id}")
    ResponseEntity<ResponseDTO> update(@PathVariable ID id, @RequestBody @Valid UpdateDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable ID id);
}
