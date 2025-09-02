package com.ms.rr.estoque_service.domain.service;

import com.mongodb.client.result.UpdateResult;
import com.ms.rr.estoque_service.adapter.output.persistence.document.Estoque;
import com.ms.rr.estoque_service.domain.dto.in.CreateEstoque;
import com.ms.rr.estoque_service.domain.exception.ProdutoNotFoundException;
import com.ms.rr.estoque_service.domain.exception.ProdutoNotFoundInEstoqueException;
import com.ms.rr.estoque_service.domain.model.EstoqueDomain;
import com.ms.rr.estoque_service.domain.port.output.EstoqueOutputPort;
import com.ms.rr.estoque_service.domain.utils.DateFormatterUtil;
import com.ms.rr.estoque_service.factory.EstoqueFactory;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.ms.rr.estoque_service.domain.exception.BaseErrorMessage.PRODUTO_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {

    @Mock
    private EstoqueOutputPort estoqueOutputPort;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private EstoqueService estoqueService;

    @Captor
    ArgumentCaptor<Aggregation> aggregationCaptor;

    @Captor
    ArgumentCaptor<Query> queryArgumentCaptor;

    @Captor
    ArgumentCaptor<Update> updateArgumentCaptor;

    private EstoqueDomain estoqueDomain;
    private CreateEstoque createEstoque;

    @BeforeEach
    void setUp() {
        estoqueDomain = EstoqueFactory.estoqueDomain();
        createEstoque = EstoqueFactory.createEstoque();
    }

    @Nested
    class SalvandoNoEstoque {

        @Test
        void shouldSaveEstoqueSuccessfullyWhenProdutoIsFound() {
            // Arrange
            var estoqueCreated = createEstoque;

            var produtoExistente = Mockito.mock(EstoqueDomain.class);

            when(estoqueOutputPort.findByNomeProduto(estoqueCreated.nomeProduto()))
                    .thenReturn(Optional.of(estoqueDomain));

            lenient().when(produtoExistente.addQuantidadeAndUpdateDataAtualizacao(5))
                    .thenReturn(any(EstoqueDomain.class));

            // Act
            estoqueService.salvar(estoqueCreated);

            // Assert
            verify(estoqueOutputPort).save(any(EstoqueDomain.class));

        }

        @Test
        void shouldSaveEstoqueSuccessfullyWhenProdutoIsNotFound() {
            // Arrange
            var estoqueCreated = createEstoque;
            var novoEstoqueCreated = Mockito.mock(CreateEstoque.class);

            when(estoqueOutputPort.findByNomeProduto(createEstoque.nomeProduto()))
                    .thenReturn(Optional.empty());

            lenient().when(novoEstoqueCreated.toDomain())
                    .thenReturn(any(EstoqueDomain.class));

            // Act
            estoqueService.salvar(estoqueCreated);

            // Assert
            verify(estoqueOutputPort, times(1)).findByNomeProduto(createEstoque.nomeProduto());
            verify(estoqueOutputPort).save(any(EstoqueDomain.class));
        }
    }

    @Nested
    class BuscandoProdutoPorNome {

        @Test
        void shouldFindAllProdutosInEstoqueByNomeProdutoSuccessfully() {
            // Arrange
            var nomeProduto = "Camisa Chelsea";

            when(estoqueOutputPort.findByNomeProduto(nomeProduto))
                    .thenReturn(Optional.of(estoqueDomain));

            // Act
            var produtoEstoque = estoqueService.buscarPorNome(nomeProduto);

            // Assert
            verify(estoqueOutputPort, times(1)).findByNomeProduto(nomeProduto);
            assertEquals(estoqueDomain.nomeProduto(), produtoEstoque.nomeProduto());
        }

        @Test
        void shouldThrowExceptionWhenProdutoIsNotFoundByNomeInEstoque() {
            // Arrange
            var nomeProduto = "Camisa Chelsea";

            when(estoqueOutputPort.findByNomeProduto(nomeProduto))
                    .thenThrow(new ProdutoNotFoundException(PRODUTO_NOT_FOUND
                            .params(nomeProduto).getMessage()));

            // Assert
            assertThrows(ProdutoNotFoundException.class, () -> estoqueService.buscarPorNome(nomeProduto));
        }
    }

    @Nested
    class BuscandoTodosProdutoNoEstoque {

        @Test
        void shouldFindAllProdutosInEstoqueSuccessfully() {
            // Arrange
            var pageRequest = PageRequest.of(0, 10);
            var page = EstoqueFactory.createWithPage();
            when(estoqueOutputPort.findAll(eq(pageRequest)))
                    .thenReturn(page);

            // Act
            var response = estoqueService.buscarTodos(pageRequest);

            // Assert
            assertNotNull(response);
            assertEquals(page.getTotalPages(), response.getTotalPages());
            assertEquals(page.getTotalElements(), response.getTotalElements());
            assertEquals(page.getSize(), response.getSize());
            assertEquals(page.getNumber(), response.getNumber());

            assertEquals(page.getContent().getFirst().nomeProduto(), response.getContent().getFirst().nomeProduto());
            assertEquals(page.getContent().getFirst().skuCode(), response.getContent().getFirst().skuCode());

            verify(estoqueOutputPort, times(1)).findAll(pageRequest);
        }
    }

    @Nested
    class BuscandoTodosPorTipoProdutoNoEstoque {

        @Test
        void shouldFindAllProdutosInEstoqueByTipoSuccessfully() {
            // Arrange
            var tipoProduto = "Roupas";
            var pageRequest = PageRequest.of(0, 10);
            var page = EstoqueFactory.createWithPage();

            when(estoqueOutputPort.findAllByTipo(eq(tipoProduto), eq(pageRequest)))
                .thenReturn(page);

            // Act
            var response = estoqueService.buscarPorTipoProduto(tipoProduto, pageRequest);

            // Assert
            assertNotNull(response);
            assertNotNull(response);
            assertEquals(page.getTotalPages(), response.getTotalPages());
            assertEquals(page.getTotalElements(), response.getTotalElements());
            assertEquals(page.getSize(), response.getSize());
            assertEquals(page.getNumber(), response.getNumber());

            assertEquals(page.getContent().getFirst().nomeProduto(), response.getContent().getFirst().nomeProduto());
            assertEquals(page.getContent().getFirst().tipoProduto(), response.getContent().getFirst().tipoProduto());
            assertEquals(page.getContent().getFirst().skuCode(), response.getContent().getFirst().skuCode());

            verify(estoqueOutputPort, times(1)).findAllByTipo(tipoProduto, pageRequest);
        }
    }

    @Nested
    class BuscandoTotalPorTipoProdutoNoEstoque {

        @Test
        void shouldFindTotalProdutosByTipoInEstoqueSuccessfully() {
            // Arrange
            var tipoProduto = "Roupas";
            var totalEsperado = BigDecimal.valueOf(1);
            var resultadoAgregado = Mockito.mock(AggregationResults.class);

            when(resultadoAgregado.getUniqueMappedResult())
                    .thenReturn(new Document("quantidade", totalEsperado));

            when(mongoTemplate.aggregate(aggregationCaptor.capture(), anyString(), eq(Document.class)))
                .thenReturn(resultadoAgregado);


            // Act
            estoqueService.buscaTotalPorTipoProduto(tipoProduto);

            // Assert
            var aggregation = aggregationCaptor.getValue();
            var aggregationExpected = newAggregation(
                    match(Criteria.where("tipoProduto").is(tipoProduto)),
                    group().sum("quantidade").as("quantidade")
            );

            assertEquals(aggregationExpected.toString(), aggregation.toString());
        }
    }

    @Nested
    class DecrementandoQuantidadeAposDeletarPorNome {

        @Test
        void shouldDecrementaQuantidadeAposDeletarPorNomeSuccessfully() {
            // Arrange
            var nomeProduto = "Camisa Chelsea";
            var quantidade = 2;
            var estoqueClass = Estoque.class;
            var updateResult = Mockito.mock(UpdateResult.class);

            when(estoqueOutputPort.findByNomeProduto(nomeProduto))
                    .thenReturn(Optional.of(estoqueDomain));

            when(mongoTemplate.updateFirst(queryArgumentCaptor.capture(), updateArgumentCaptor.capture(), eq(estoqueClass)))
                    .thenReturn(updateResult);

            // Act
            estoqueService.decrementaPorNome(nomeProduto, quantidade);

            // Assert
            var query = queryArgumentCaptor.getValue();
            var queryExpected = new Query(Criteria.where("nomeProduto").is(nomeProduto));
            var update = updateArgumentCaptor.getValue();
            var updatedExpected = new Update()
                    .inc("quantidade",  -quantidade)
                    .set("dataAtualizacao", LocalDateTime.now().format(DateFormatterUtil.customFormatter()));

            assertEquals(queryExpected.toString(), query.toString());
            assertEquals(updatedExpected.toString(), update.toString());
        }

        @Test
        void shouldThrowAnExceptionWhenProdutoNotFound() {
            // Arrange
            var nomeProduto = "Camisa Chelsea";
            var quantidade = 2;

            when(estoqueOutputPort.findByNomeProduto(nomeProduto))
                    .thenThrow(new ProdutoNotFoundException(PRODUTO_NOT_FOUND
                            .params(nomeProduto).getMessage()));

            // Act Assert
            assertThrows(ProdutoNotFoundException.class, () ->
                    estoqueService.decrementaPorNome(nomeProduto, quantidade));
        }

        @Test
        void shouldThrowAnExceptionWhenProdutoNotFoundInEstoque() {
            // Arrange
            var nomeProduto = "Camisa Chelsea";
            var quantidade = 2;

            when(estoqueOutputPort.findByNomeProduto(nomeProduto))
                    .thenThrow(new ProdutoNotFoundInEstoqueException(PRODUTO_NOT_FOUND
                            .params(nomeProduto).getMessage()));

            // Act Assert
            assertThrows(ProdutoNotFoundInEstoqueException.class, () ->
                    estoqueService.decrementaPorNome(nomeProduto, quantidade));
        }
    }
}