package com.ms.rr.estoque_service.adapter.input.rest.impl;

import com.ms.rr.estoque_service.adapter.input.rest.EstoqueResource;
import com.ms.rr.estoque_service.domain.dto.out.ApiResponse;
import com.ms.rr.estoque_service.domain.dto.out.EstoqueResponse;
import com.ms.rr.estoque_service.domain.dto.out.PaginationResponse;
import com.ms.rr.estoque_service.domain.port.input.EstoqueUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueResourceImpl implements EstoqueResource {

    private final static Logger log = LoggerFactory.getLogger(EstoqueResourceImpl.class);

    private final EstoqueUseCase estoqueUseCase;

    public EstoqueResourceImpl(EstoqueUseCase estoqueUseCase) {
        this.estoqueUseCase = estoqueUseCase;
    }

    @Override
    public ResponseEntity<EstoqueResponse> findByNome(String nomeProduto) {
        log.info("Buscando no estoque produto: {}", nomeProduto);
        return ResponseEntity.ok(estoqueUseCase.buscarPorNome(nomeProduto));
    }

    @Override
    public ResponseEntity<ApiResponse<EstoqueResponse>> findAll(Integer page, Integer pageSize) {
        var pageResponse = estoqueUseCase.buscarTodos(PageRequest.of(page, pageSize));
        var totalOnEstoque = pageResponse.getTotalElements();
        log.info("Consultando estoque");
        return ResponseEntity.ok(new ApiResponse<>(
                Map.of("totalOnEstoque", totalOnEstoque),
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));
    }

    @Override
    public ResponseEntity<ApiResponse<EstoqueResponse>> findAllByTipo(String tipo, Integer page, Integer pageSize) {
        var pageResponse = estoqueUseCase.buscarPorTipoProduto(tipo, PageRequest.of(page, pageSize));
        var totalOnTipo = estoqueUseCase.buscaTotalPorTipoProduto(tipo);
        log.info("Consultando estoque por tipo: {}", tipo);
        return ResponseEntity.ok(new ApiResponse<>(
                Map.of("totalOnEstoque", totalOnTipo),
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));
    }

    @Override
    public ResponseEntity<String> deleteByName(String nomeProduto, Integer quantidade) {
        log.info("Removendo produto do estoque: {}", nomeProduto);
        estoqueUseCase.decrementaPorNome(nomeProduto, quantidade);
        return ResponseEntity.ok("Produto removido do Estoque");
    }
}
