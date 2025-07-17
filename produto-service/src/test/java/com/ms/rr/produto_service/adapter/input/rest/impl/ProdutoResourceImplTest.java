package com.ms.rr.produto_service.adapter.input.rest.impl;

import com.ms.rr.produto_service.adapter.input.handler.*;
import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.dto.out.ProdutoResponse;
import com.ms.rr.produto_service.domain.port.input.ProdutoUseCase;
import com.ms.rr.produto_service.factory.ProdutoFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@WebFluxTest(controllers = ProdutoResourceImpl.class)
@Import({FornecedorNotFoundHandler.class,
        ProductNotFoundHandler.class,
        InvalidPaginationHandler.class,
        GenericHanlder.class})
class ProdutoResourceImplTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private ProdutoUseCase produtoUseCase;

    @Nested
    class CreateProdutoResource {

        @Test
        void shouldCreateProdutoSuccessfully() {
            // Arrange
            CreateProduto createProduto = ProdutoFactory.createProduto();
            ProdutoResponse produtoResponse = ProdutoFactory.buildProdutoResponse();

            when(produtoUseCase.salvar(any(CreateProduto.class)))
                    .thenReturn(Mono.just(produtoResponse));

            webTestClient.post()
                    .uri("/api/produto")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(createProduto)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ProdutoResponse.class)
                    .isEqualTo(produtoResponse);
        }
    }
}