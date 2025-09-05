package com.ms.rr.estoque_service.adapter.input.rest.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.rr.estoque_service.domain.dto.out.ApiResponse;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

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

    @Captor
    ArgumentCaptor<PageRequest> pageRequestCaptor;

    @Captor
    ArgumentCaptor<String> tipoProdutoCaptor;

    @Captor
    ArgumentCaptor<Integer> quantidadeCaptor;

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
            var mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                    "/api/estoque/{nomeProduto}", nomeProduto)
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
            // Arrange
            var nomeProduto = "Camisa Chelsea";

            when(estoqueUseCase.buscarPorNome(nomeProduto))
                    .thenThrow(new ProdutoNotFoundException(PRODUTO_NOT_FOUND
                            .params(nomeProduto).getMessage()));

            // Act & Assert
            mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/{nomeProduto}", nomeProduto)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProdutoNotFoundException))
                    .andExpect(result -> assertEquals(PRODUTO_NOT_FOUND
                            .params(nomeProduto).getMessage(), result.getResolvedException().getMessage()));

            assertThrows(ProdutoNotFoundException.class, () -> estoqueUseCase.buscarPorNome(nomeProduto));
        }
    }

    @Nested
    class FindAllProductsInEstoque {

        @Test
        void shouldFindAllProductsInEstoqueSuccessfully() throws Exception {
            // Arrange
            int page = 0;
            int pageSize = 3;
            var estoqueResponsePagination = EstoqueFactory.createWithPageEstoqueResponse();

            when(estoqueUseCase.buscarTodos(pageRequestCaptor.capture()))
                    .thenReturn(estoqueResponsePagination);

            // Act & Assert
            var mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque")
                            .param("page", Integer.toString(page))
                            .param("pageSize", Integer.toString(pageSize))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.summary.totalOnEstoque").value(3))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.pagination.page").value(0))
                    .andExpect(jsonPath("$.pagination.pageSize").value(3))
                    .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();
            var response = objectMapper.readValue(responseBody, ApiResponse.class);

            assertEquals(response.data().size(), estoqueResponsePagination.getContent().size());
        }
    }

    @Nested
    class FindAllProductsInEstoqueByTipo {

        @Test
        void shouldFindAllProductsInEstoqueByTipoSuccessfully() throws Exception {
            // Arrange
            int page = 0;
            int pageSize = 3;
            var tipoProduto = "Roupas";
            var estoqueResponsePagination = EstoqueFactory.createWithPageEstoqueResponse();
            var totalEsperado = BigDecimal.valueOf(3);

            when(estoqueUseCase.buscarPorTipoProduto(tipoProdutoCaptor.capture(), pageRequestCaptor.capture()))
                    .thenReturn(estoqueResponsePagination);

            when(estoqueUseCase.buscaTotalPorTipoProduto(tipoProdutoCaptor.capture()))
                    .thenReturn(totalEsperado);

            // Act & Assert
            var mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                    "/api/estoque/tipo-produto/{tipoProduto}", tipoProduto)
                            .param("page", Integer.toString(page))
                            .param("pageSize", Integer.toString(pageSize))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.summary.totalOnEstoque").value(totalEsperado))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.pagination.page").value(0))
                    .andExpect(jsonPath("$.pagination.pageSize").value(3))
                    .andReturn();


            String responseBody = mvcResult.getResponse().getContentAsString();
            var response = objectMapper.readValue(responseBody, ApiResponse.class);

            assertEquals(response.data().size(), estoqueResponsePagination.getContent().size());
            assertEquals(response.summary().get("totalOnEstoque"), totalEsperado.intValueExact());
        }
    }

    @Nested
    class DeleteProdutoFromEstoqueAndUpdateQuantidade {

        @Test
        void shouldDeleteProdutoFromEstoqueAndUpdateQuantidadeSuccessfully() throws Exception {
            // Arrange
            var nomeProduto = "Camisa Chelsea";
            var quantidade = 2;

            doNothing().when(estoqueUseCase).decrementaPorNome(nomeProdutoCaptor.capture(), quantidadeCaptor.capture());

            // Act & Assert
            var mockResult = mockMvc.perform(MockMvcRequestBuilders.delete(
                    "/api/estoque/nome-produto/{nomeProduto}", nomeProduto)
                    .param("quantidade", String.valueOf(quantidade))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String responseBody = mockResult.getResponse().getContentAsString();
            assertEquals(responseBody, "Produto removido do Estoque");
        }

        @Test
        void shouldThrowExceptionWhenProdutoNotFound() throws Exception {
            // Arrange
            var nomeProduto = "Camisa Chelsea";
            var quantidade = 2;

            doThrow(new ProdutoNotFoundException(PRODUTO_NOT_FOUND.params(nomeProduto).getMessage()))
                    .when(estoqueUseCase).decrementaPorNome(nomeProdutoCaptor.capture(), quantidadeCaptor.capture());

            // Act & Assert
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/estoque/nome-produto/{nomeProduto}", nomeProduto)
                            .param("quantidade", String.valueOf(quantidade))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProdutoNotFoundException))
                    .andExpect(result -> assertEquals(PRODUTO_NOT_FOUND
                            .params(nomeProduto).getMessage(), result.getResolvedException().getMessage()));

            assertThrows(ProdutoNotFoundException.class, () -> estoqueUseCase.decrementaPorNome(nomeProduto, quantidade));
        }
    }
}