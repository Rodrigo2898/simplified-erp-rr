package com.ms.rr.estoque_service.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.rr.estoque_service.adapter.input.handler.ApiError;
import com.ms.rr.estoque_service.domain.dto.out.EstoqueResponse;
import com.ms.rr.estoque_service.domain.exception.ProdutoNotFoundException;
import com.ms.rr.estoque_service.domain.port.input.EstoqueUseCase;
import com.ms.rr.estoque_service.factory.ErrorResponseFactory;
import com.ms.rr.estoque_service.factory.EstoqueFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static com.ms.rr.estoque_service.domain.exception.BaseErrorMessage.PRODUTO_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EstoqueResourceIT extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EstoqueUseCase estoqueUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    String nomeProduto;

    @BeforeEach
    public void setUp() throws Exception {
        var createProdutoEstoque = EstoqueFactory.createEstoque();
        estoqueUseCase.salvar(createProdutoEstoque);
        nomeProduto = createProdutoEstoque.nomeProduto();
    }

    @Nested
    class FindProdutoInEstoqueByNome {

        @Test
        void shouldFindProdutoInEstoqueByNomeSuccessfully() throws Exception {
            var estoqueDomain = EstoqueFactory.estoqueDomain();
            var estoqueResponse = EstoqueFactory.createEstoqueResponse(estoqueDomain);

            var mvcResult = mockMvc.perform(get("/api/estoque/{nomeProduto}",nomeProduto)
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();


            String responseBody = mvcResult.getResponse().getContentAsString();
            var response = objectMapper.readValue(responseBody, EstoqueResponse.class);

            assertEquals(response.nomeProduto(), estoqueResponse.nomeProduto());
            assertEquals(response.skuCode(), estoqueResponse.skuCode());
        }

        @Test
        void shouldThrowExceptionWhenFindProdutoInEstoqueByNomeNotFound() throws Exception {
            var nomeIcorreto = "Camisa Liverpool";
            var apiError = ErrorResponseFactory.buildApiError(
                    HttpStatus.NOT_FOUND.value(),
                    PRODUTO_NOT_FOUND.getMessage(),
                    LocalDateTime.now()
            );

            var mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/{nomeIcorreto}", nomeIcorreto)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProdutoNotFoundException))
                    .andExpect(result -> assertEquals(PRODUTO_NOT_FOUND
                            .params(nomeIcorreto).getMessage(), result.getResolvedException().getMessage()))
                    .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();
            var response = objectMapper.readValue(responseBody, ApiError.class);

            assertEquals(response.status(), apiError.status());
            assertEquals(response.description(), apiError.description());;
        }
    }
}
