package com.ms.rr.estoque_service.domain.service;

import com.ms.rr.estoque_service.domain.dto.in.CreateEstoque;
import com.ms.rr.estoque_service.domain.exception.ProdutoNotFoundException;
import com.ms.rr.estoque_service.domain.model.EstoqueDomain;
import com.ms.rr.estoque_service.domain.port.output.EstoqueOutputPort;
import com.ms.rr.estoque_service.factory.EstoqueFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

import static com.ms.rr.estoque_service.domain.exception.BaseErrorMessage.PRODUTO_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {

    @Mock
    private EstoqueOutputPort estoqueOutputPort;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private EstoqueService estoqueService;

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
}