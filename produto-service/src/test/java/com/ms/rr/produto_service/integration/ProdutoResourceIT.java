package com.ms.rr.produto_service.integration;

import com.ms.rr.produto_service.domain.dto.out.ProdutoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

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


    @Test
    public void criarProdutos() {
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
}
