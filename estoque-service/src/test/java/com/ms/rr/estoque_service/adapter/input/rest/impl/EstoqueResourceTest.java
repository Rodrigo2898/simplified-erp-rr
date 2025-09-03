package com.ms.rr.estoque_service.adapter.input.rest.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.rr.estoque_service.domain.dto.out.EstoqueResponse;
import com.ms.rr.estoque_service.domain.exception.ProdutoNotFoundException;
import com.ms.rr.estoque_service.domain.model.EstoqueDomain;
import com.ms.rr.estoque_service.domain.port.input.EstoqueUseCase;
import com.ms.rr.estoque_service.factory.EstoqueFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.ms.rr.estoque_service.domain.exception.BaseErrorMessage.PRODUTO_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EstoqueResourceImpl.class)
class EstoqueResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EstoqueUseCase estoqueUseCase;

    @Captor
    ArgumentCaptor<String> nomeProdutoCaptor;

    private EstoqueDomain estoqueDomain;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        estoqueDomain = EstoqueFactory.estoqueDomain();
        objectMapper = new ObjectMapper();
    }

    @Nested
    class FindProdutoInEstoqueByNome {

        @Test
        void shouldFindProdutoInEstoqueByNomeSuccessfully() throws Exception {
            // Arrange
            var nomeProduto = "Camisa Chelsea";
            var estoqueResponse = EstoqueFactory.createEstoqueResponse(estoqueDomain);

            when(estoqueUseCase.buscarPorNome(nomeProdutoCaptor.capture()))
                    .thenReturn(estoqueResponse);

            // Act
            var mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/{nomeProduto}", nomeProduto)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            // Assert
            String responseBody = mvcResult.getResponse().getContentAsString();
            var response = objectMapper.readValue(responseBody, EstoqueResponse.class);

            assertEquals(response.nomeProduto(), estoqueResponse.nomeProduto());
            assertEquals(response.skuCode(), estoqueResponse.skuCode());
            assertEquals(response.dataAtualizacao(), estoqueResponse.dataAtualizacao());

            verify(estoqueUseCase, times(1)).buscarPorNome(nomeProduto);
            assertEquals(nomeProdutoCaptor.getValue(), nomeProduto);
        }

        @Test
        void shouldThrowExceptionWhenFindProdutoInEstoqueByNomeNotFound() throws Exception {
            var nomeProduto = "Camisa Chelsea";

            when(estoqueUseCase.buscarPorNome(nomeProduto))
                    .thenThrow(new ProdutoNotFoundException(PRODUTO_NOT_FOUND
                            .params(nomeProduto).getMessage()));

            mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/{nomeProduto}", nomeProduto)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProdutoNotFoundException))
                    .andExpect(result -> assertEquals(PRODUTO_NOT_FOUND
                            .params(nomeProduto).getMessage(), result.getResolvedException().getMessage()));
        }
    }
}