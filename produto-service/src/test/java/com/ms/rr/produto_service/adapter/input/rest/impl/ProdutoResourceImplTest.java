package com.ms.rr.produto_service.adapter.input.rest.impl;

import com.ms.rr.produto_service.adapter.input.handler.*;
import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.dto.in.UpdateProduto;
import com.ms.rr.produto_service.domain.dto.out.ProdutoResponse;
import com.ms.rr.produto_service.domain.exception.FornecedorNotFoundException;
import com.ms.rr.produto_service.domain.exception.ProdutoNotFoundException;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.ms.rr.produto_service.domain.exception.BaseErrorMessage.FORNECEDOR_NOT_FOUND;
import static com.ms.rr.produto_service.domain.exception.BaseErrorMessage.PRODUTO_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

        @Test
        void shouldThrowExceptionWhenFornecedorNotFound() {
            CreateProduto createProduto = ProdutoFactory.createProduto();
            Long fornecedorId = createProduto.fornecedorId();

            FornecedorNotFoundException exception = new FornecedorNotFoundException(
                    FORNECEDOR_NOT_FOUND.params(fornecedorId.toString()).getMessage()
            );

            when(produtoUseCase.salvar(any(CreateProduto.class)))
                    .thenReturn(Mono.error(exception));


            webTestClient.post()
                    .uri("/api/produto")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(createProduto)
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody()
                    .jsonPath("$.status").isEqualTo("404")
                    .jsonPath("$.message").isEqualTo(FORNECEDOR_NOT_FOUND.params(fornecedorId.toString()).getMessage());
        }
    }


    @Nested
    class FindProdutoByIdResource {

        @Test
        void shouldFindProdutoByIdSuccessfully() {
            Long id = 1L;
            ProdutoResponse produtoResponse = ProdutoFactory.buildProdutoResponse();

            when(produtoUseCase.buscarPorId(anyLong()))
                    .thenReturn(Mono.just(produtoResponse));

            webTestClient.get()
                    .uri("/api/produto/{id}", id)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ProdutoResponse.class);
        }

        @Test
        void shouldThrowExceptionWhenProdutoNotFound() {
            Long id = 1L;
            ProdutoNotFoundException exception = new ProdutoNotFoundException(PRODUTO_NOT_FOUND
                    .params(id.toString()).getMessage());

            when(produtoUseCase.buscarPorId(anyLong()))
                    .thenReturn(Mono.error(exception));

            webTestClient.get()
                    .uri("/api/produto/{id}", id)
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody()
                    .jsonPath("$.status").isEqualTo("404")
                    .jsonPath("$.message").isEqualTo(PRODUTO_NOT_FOUND.params(id.toString()).getMessage());
        }
    }

    @Nested
    class FindAllProdutosResource {

        @Test
        void shouldFindAllProdutosSuccessfully() {
            var produto1 = ProdutoFactory.buildProdutoWithId(1L);
            var produto2 = ProdutoFactory.buildProdutoWithId(2L);

            when(produtoUseCase.buscarTodosProdutos())
                    .thenReturn(Flux.just(produto1, produto2)
                            .map(ProdutoResponse::fromDomain));

            webTestClient.get()
                    .uri("/api/produto")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBodyList(ProdutoResponse.class);
        }

        @Test
        void shouldThrowExceptionWhenProdutoNotFound() {}
    }

    @Nested
    class FindProdutosByCategoria {

        @Test
        void shouldFindProdutosByCategoriaSuccessfully() {
            String categoria = "Roupas";
            var produto1 = ProdutoFactory.buildProdutoWithCategoria(categoria);
            var produto2 = ProdutoFactory.buildProdutoWithCategoria(categoria);

            when(produtoUseCase.buscarProdutosPorCategoria(categoria))
                    .thenReturn(Flux.just(produto1, produto2)
                            .map(ProdutoResponse::fromDomain));

            webTestClient.get()
                    .uri("/api/produto/categoria-produto/{categoria}", categoria)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBodyList(ProdutoResponse.class);
        }

        @Test
        void shouldThrowExceptionWhenCategoriaNotFound() {}
    }

    @Nested
    class UpdateProdutoResource {

        @Test
        void shouldUpdateProdutoSuccessfully() {
            var id = 1L;
            var update = ProdutoFactory.buildUpdateProduto();
            var produtoWithId = ProdutoFactory.buildProdutoWithId(id);

            when(produtoUseCase.atualizar(anyLong(), any(UpdateProduto.class)))
                    .thenReturn(Mono.just(ProdutoResponse.fromDomain(produtoWithId)));

            webTestClient.put()
                    .uri("/api/produto/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(update)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ProdutoResponse.class)
                    .isEqualTo(ProdutoResponse.fromDomain(produtoWithId));
        }

        @Test
        void shouldThrowExceptionWhenProdutoNotFound() {
            var id = 1L;
            var update = ProdutoFactory.buildUpdateProduto();
            ProdutoNotFoundException exception = new ProdutoNotFoundException(PRODUTO_NOT_FOUND
                    .params(Long.toString(id)).getMessage());

            when(produtoUseCase.atualizar(anyLong(), any(UpdateProduto.class)))
                    .thenReturn(Mono.error(exception));

            webTestClient.put()
                    .uri("/api/produto/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(update)
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody()
                    .jsonPath("$.status").isEqualTo("404")
                    .jsonPath("$.message").isEqualTo(PRODUTO_NOT_FOUND.params(Long.toString(id)).getMessage());
        }
    }

    @Nested
    class DeleteProdutoResource {

        @Test
        void shouldDeleteProdutoSuccessfully() {
            var id = 1L;

            when(produtoUseCase.excluir(anyLong()))
                    .thenReturn(Mono.empty());

            webTestClient.delete()
                    .uri("/api/produto/{id}", id)
                    .exchange()
                    .expectStatus().isNoContent()
                    .expectBody();
        }

        @Test
        void shouldThrowExceptionWhenProdutoNotFound() {
            var id = 1L;
            ProdutoNotFoundException exception = new ProdutoNotFoundException(PRODUTO_NOT_FOUND
                    .params(Long.toString(id)).getMessage());

            when(produtoUseCase.excluir(anyLong()))
                    .thenReturn(Mono.error(exception));

            webTestClient.delete()
                    .uri("/api/produto/{id}", id)
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody()
                    .jsonPath("$.status").isEqualTo("404")
                    .jsonPath("$.message").isEqualTo(PRODUTO_NOT_FOUND.params(Long.toString(id)).getMessage());
        }
    }
}