package com.ms.rr.produto_service.adapter.input.rest.impl;

import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.dto.out.ProdutoResponse;
import com.ms.rr.produto_service.domain.port.input.ProdutoUseCase;
import com.ms.rr.produto_service.factory.ProdutoFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;

@WebFluxTest(controllers = ProdutoResourceImpl.class)
class ProdutoResourceImplTest {

    @MockitoBean
    private ProdutoUseCase produtoUseCase;

    @Autowired
    private WebTestClient webTestClient;

    @Nested
    class CreateProdutoResource {

        @Test
        void shouldCreateProdutoSuccessfully() {
            var in = ProdutoFactory.createProduto();
            var out = ProdutoFactory.buildProdutoResponse();

            when(produtoUseCase.salvar(in))
                    .thenReturn(Mono.just(out));


            webTestClient.post()
                    .uri("/api/produto")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(in)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.id").isNotEmpty()
                    .jsonPath("$.nome").isEqualTo("CAMISA Barcelona");
        }
    }
}