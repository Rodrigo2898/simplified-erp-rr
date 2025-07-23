package com.ms.rr.produto_service.integration;

import com.ms.rr.produto_service.domain.dto.out.ProdutoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.ms.rr.produto_service.domain.exception.BaseErrorMessage.FORNECEDOR_NOT_FOUND;

public class ProdutoResourceIT extends AbstractIntegrationTest {

    @BeforeEach
    void setupFornecedorStub() {
        configureFor(wiremock.getHost(), wiremock.getMappedPort(8080));
    }


    @Nested
    class CriarProdutoIT {
        @Test
        void createProduto() {

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
}
