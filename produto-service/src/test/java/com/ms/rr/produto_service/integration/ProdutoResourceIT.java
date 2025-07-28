package com.ms.rr.produto_service.integration;

import com.ms.rr.produto_service.domain.dto.out.ProdutoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.ms.rr.produto_service.domain.exception.BaseErrorMessage.FORNECEDOR_NOT_FOUND;
import static com.ms.rr.produto_service.domain.exception.BaseErrorMessage.PRODUTO_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProdutoResourceIT extends AbstractIntegrationTest {

    @BeforeEach
    void setupFornecedorStub() {
        configureFor(wiremock.getHost(), wiremock.getMappedPort(8080));

        stubFor(get(urlPathMatching("/api/fornecedor/672978073"))
                .willReturn(okJson("""
                    {
                      "id": 672978073,
                      "nome": "Fornecedor Teste",
                      "email": "teste@fornecedor.com",
                      "telefone": "61999999999",
                      "cpnj": "12345678000199",
                      "razaoSocial": "Fornecedor Teste Ltda"
                    }
                """)));
    }


    @Nested
    class CriarProdutoIT {
        @Test
        void createProduto() {

            String requestBody = """
                {
                    "nome":"Camisa Chelsea",
                    "descricao":"II Chelsea 24/25",
                    "categoria":"Roupas",
                    "preco":"280.90",
                    "fornecedorId":672978073
                }
                """;

            webTestClient.post()
                    .uri("/api/produto")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ProdutoResponse.class);
        }

        @Test
        void errorFornecedorNotFound() {
            Long fornecedorId = 763878074L;
            LocalDateTime timestamp = LocalDateTime.now();

            stubFor(get(urlEqualTo("/api/fornecedor/" + fornecedorId))
                    .willReturn(aResponse()
                            .withStatus(404)
                            .withHeader("Content-Type", "application/json")
                            .withBody(String.format("""
                                    {
                                        "status": 404,
                                        "message": "%s",
                                        "timestamp": "%s"
                                    }
                                    """,
                                    FORNECEDOR_NOT_FOUND.params(String.valueOf(fornecedorId)).getMessage(),
                                    timestamp
                            ))));


            String requestBody = String.format("""
                {
                    "nome":"Camisa Chelsea",
                    "descricao":"II Chelsea 24/25",
                    "categoria":"Roupas",
                    "preco":"280.90",
                    "fornecedorId": %s
                }
                """, fornecedorId);

            webTestClient.post()
                    .uri("/api/produto")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody()
                    .jsonPath("$.status").isEqualTo("404")
                    .jsonPath("$.message").isEqualTo(FORNECEDOR_NOT_FOUND.params(fornecedorId.toString()).getMessage());
        }
    }


    @Nested
    class BuscarProdutoIT {

        @Test
        void findProduto() {
            String json = """
                {
                    "nome":"Camisa Chelsea",
                    "descricao":"II Chelsea 24/25",
                    "categoria":"Roupas",
                    "preco":"280.90",
                    "fornecedorId":672978073
                }
                """;

            ProdutoResponse produtoResponse = webTestClient.post()
                    .uri("/api/produto")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(json)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody(ProdutoResponse.class)
                    .returnResult()
                    .getResponseBody();

            assert produtoResponse != null;
            var id = produtoResponse.id();

            webTestClient.get()
                    .uri("/api/produto/{id}", id)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ProdutoResponse.class)
                    .consumeWith(result -> {
                        ProdutoResponse produto = result.getResponseBody();
                        assertNotNull(produto);
                        assertEquals("Camisa Chelsea", produto.nome());
                    });
        }

        @Test
        void errorProdutoNotFound() {
            Long id = 12345L;
            webTestClient.get()
                    .uri("/api/produto/{id}", id)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.status").isEqualTo("404")
                    .jsonPath("$.message").isEqualTo(PRODUTO_NOT_FOUND.params(id.toString()).getMessage());
        }
    }

    @Nested
    class BuscandoTodosProdutosIT {

        @Test
        void findAllProducts() {
            createTestProduto("Camisa Barcelona", "II Home KIT");
            createTestProduto("Camisa Chelsea", "II AWAY KIT");

            webTestClient.get()
                    .uri("/api/produto")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBodyList(ProdutoResponse.class);
        }
    }

    @Nested
    class BuscandoProdutosPorCategoriaIT {

        @Test
        void findAllProductsByCategoria() {
            createTestProduto("Camisa Barcelona", "II Home KIT");
            createTestProduto("Camisa Chelsea", "II AWAY KIT");

            webTestClient.get()
                    .uri("/api/produto/categoria-produto/{categoria}", "Roupas")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBodyList(ProdutoResponse.class);
        }
    }

    @Nested
    class AtualizandoProdutoIT {

        @Test
        void updateProduto() {
            String json = """
                {
                    "nome":"Camisa Chelsea",
                    "descricao":"II Chelsea 24/25",
                    "categoria":"Roupas",
                    "preco":"280.90",
                    "fornecedorId":672978073
                }
                """;

            ProdutoResponse produtoResponse = webTestClient.post()
                    .uri("/api/produto")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(json)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody(ProdutoResponse.class)
                    .returnResult()
                    .getResponseBody();

            assert produtoResponse != null;
            var id = produtoResponse.id();

            String jsonUpdateProduto = """
                {
                    "nome":"Camisa Chelsea",
                    "descricao":"II Chelsea 24/25",
                    "categoria":"Roupas",
                    "preco":"250.90",
                    "fornecedorId":672978073
                }
                """;

            webTestClient.put()
                    .uri("/api/produto/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(jsonUpdateProduto)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(ProdutoResponse.class);
        }
    }

    @Nested
    class DeletandoProdutoIT {

        @Test
        void deleteProduto() {
            String json = """
                {
                    "nome":"Camisa Chelsea",
                    "descricao":"II Chelsea 24/25",
                    "categoria":"Roupas",
                    "preco":"280.90",
                    "fornecedorId":672978073
                }
                """;

            ProdutoResponse produtoResponse = webTestClient.post()
                    .uri("/api/produto")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(json)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody(ProdutoResponse.class)
                    .returnResult()
                    .getResponseBody();

            assert produtoResponse != null;
            var id = produtoResponse.id();

            webTestClient.delete()
                    .uri("/api/produto/{id}", id)
                    .exchange()
                    .expectStatus().isNoContent()
                    .expectBody();
        }
    }

    private void createTestProduto(String nome, String descricao) {
        String json = String.format("""
                            {
                                "nome":"%s",
                                "descricao":"%s",
                                "categoria":"Roupas",
                                "preco":"280.90",
                                "fornecedorId":672978073
                            }
                        """, nome, descricao);

        webTestClient.post()
                .uri("/api/produto")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isCreated();
    }
}
