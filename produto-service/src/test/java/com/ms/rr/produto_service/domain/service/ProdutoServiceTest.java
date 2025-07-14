package com.ms.rr.produto_service.domain.service;

import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.dto.out.ProdutoResponse;
import com.ms.rr.produto_service.domain.exception.FornecedorNotFoundException;
import com.ms.rr.produto_service.domain.exception.ProdutoNotFoundException;
import com.ms.rr.produto_service.domain.model.FornecedorDomain;
import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import com.ms.rr.produto_service.domain.port.output.FornecedorOutputPort;
import com.ms.rr.produto_service.domain.port.output.ProdutoOutputPort;
import com.ms.rr.produto_service.factory.FornecedorFactory;
import com.ms.rr.produto_service.factory.ProdutoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static com.ms.rr.produto_service.domain.exception.BaseErrorMessage.FORNECEDOR_NOT_FOUND;
import static com.ms.rr.produto_service.domain.exception.BaseErrorMessage.PRODUTO_NOT_FOUND;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoOutputPort produtoOutputPort;

    @Mock
    private FornecedorOutputPort fornecedorOutputPort;

    @InjectMocks
    private ProdutoService produtoService;

    private ProdutoDomain domain;
    private CreateProduto createProduto;
    private FornecedorDomain fornecedorDomain;

    @BeforeEach
    void setUp() {
        domain = ProdutoFactory.buildProduto();
        createProduto = ProdutoFactory.createProduto();
        fornecedorDomain = FornecedorFactory.createFornecedor();
    }

    @Nested
    class SaveProduto {

        @Test
        void shouldSaveProdutoSuccessfully() {
            //Arrange
            when(fornecedorOutputPort.findFornecedorById(anyLong()))
                    .thenReturn(Mono.just(fornecedorDomain));
            when(produtoOutputPort.save(any(ProdutoDomain.class)))
                    .thenReturn(Mono.just(createProduto.toDomain()));

            // Act
            Mono<ProdutoResponse> responseMono = produtoService.salvar(createProduto);

            // Assert
            StepVerifier.create(responseMono)
                    .expectNextMatches(produtoResponse -> produtoResponse.nome().equals(createProduto.toDomain().nome()))
                    .verifyComplete();

            verify(fornecedorOutputPort).findFornecedorById(createProduto.fornecedorId());
            verify(produtoOutputPort).save(any(ProdutoDomain.class));
        }

        @Test
        void shouldThrowExceptionWhenFornecedorNotFound() {
            // Arrange
            when(fornecedorOutputPort.findFornecedorById(anyLong()))
                    .thenReturn(Mono.error(new FornecedorNotFoundException(
                            FORNECEDOR_NOT_FOUND.params(createProduto.fornecedorId().toString()).getMessage())));

            // Act
            Mono<ProdutoResponse> responseMono = produtoService.salvar(createProduto);

            // Assert
            StepVerifier.create(responseMono)
                    .expectErrorMatches(throwable -> throwable instanceof FornecedorNotFoundException &&
                            throwable.getMessage().equals(FORNECEDOR_NOT_FOUND
                                    .params(createProduto.fornecedorId().toString()).getMessage()))
                    .verify();
        }
    }

    @Nested
    class BuscandoProdutoPorId {

        @Test
        void shouldFindProdutoSuccessfully() {
            // Arrange
            var id = 987456321L;
            var response = ProdutoFactory.buildProdutoResponse();

            when(produtoOutputPort.findById(id))
                    .thenReturn(Mono.just(domain));

            // Act
            Mono<ProdutoResponse> responseMono = produtoService.buscarPorId(id);

            // Assert
            StepVerifier.create(responseMono)
                    .expectNext(response)
                    .verifyComplete();

            verify(produtoOutputPort, times(1)).findById(id);
            verifyNoMoreInteractions(produtoOutputPort);
        }

        @Test
        void shouldThrowExceptionWhenProdutoNotFound() {
            Long id = 987456321L;
            when(produtoOutputPort.findById(domain.id()))
                    .thenReturn(Mono.error(new ProdutoNotFoundException(
                            PRODUTO_NOT_FOUND.params(id.toString()).getMessage()
                    )));

            // Act
            Mono<ProdutoResponse> responseMono = produtoService.buscarPorId(id);

            // Assert
            StepVerifier.create(responseMono)
                    .expectErrorMatches(throwable -> throwable instanceof ProdutoNotFoundException &&
                            throwable.getMessage().equals(PRODUTO_NOT_FOUND
                                    .params(domain.fornecedorId().toString()).getMessage()))
                    .verify();

            verify(produtoOutputPort, times(1)).findById(id);
            verifyNoMoreInteractions(produtoOutputPort);
        }
    }

    @Nested
    class BuscandoTodosOsProdutos {

        @Test
        void shouldFindAllProdutosSuccessfully() {
            // Arrange
            var produto1 = ProdutoFactory.buildProdutoWithId(1L);
            var produto2 = ProdutoFactory.buildProdutoWithId(2L);

            when(produtoOutputPort.findAll())
                    .thenReturn(Flux.just(produto1, produto2));

            // Act
            Flux<ProdutoResponse> responseFlux = produtoService.buscarTodosProdutos();

            // Assert
            StepVerifier.create(responseFlux)
                    .expectNext(ProdutoResponse.fromDomain(produto1))
                    .expectNext(ProdutoResponse.fromDomain(produto2))
                    .verifyComplete();

            verify(produtoOutputPort, times(1)).findAll();
            verifyNoMoreInteractions(produtoOutputPort);
        }

        @Test
        void shouldThrowExceptionWhenProdutoNotFound() {}
    }

    @Nested
    class BuscandoProdutosPorCategoria {

        @Test
        void shouldfindAllByCategoriaSuccessfully() {
            // Arrange
            String categoria = "Roupas";
            var produto1 = ProdutoFactory.buildProdutoWithCategoria(categoria);
            var produto2 = ProdutoFactory.buildProdutoWithCategoria(categoria);

            when(produtoOutputPort.findAllByCategoria(categoria))
                    .thenReturn(Flux.just(produto1, produto2));

            // Act
            Flux<ProdutoResponse> responseFlux = produtoService.buscarProdutosPorCategoria(categoria);

            // Assert
            StepVerifier.create(responseFlux)
                    .expectNext(ProdutoResponse.fromDomain(produto1))
                    .expectNext(ProdutoResponse.fromDomain(produto2))
                    .verifyComplete();

            verify(produtoOutputPort, times(1)).findAllByCategoria(categoria);
            verifyNoMoreInteractions(produtoOutputPort);
        }

        @Test
        void shouldThrowExceptionWhenCategoriaNotFound() {}
    }

    @Nested
    class AtualizandoProduto {

        @Test
        void shouldUpdateProdutoSuccessfully() {
            // Arrange
            Long id = 987456321L;
            var update = ProdutoFactory.buildUpdateProduto();
            var domainWithId = ProdutoFactory.buildProdutoWithId(id);

            when(produtoOutputPort.findById(id))
                    .thenReturn(Mono.just(domain));
            when(produtoOutputPort.save(any()))
                    .thenReturn(Mono.just(domainWithId));


            // Act
            Mono<ProdutoResponse> result = produtoService.atualizar(id, update);

            // Assert
            StepVerifier.create(result)
                    .expectNextMatches(response ->
                            response.id().equals(id)
                                    && response.nome().equals(domainWithId.nome())
                                    && response.descricao().equals(domainWithId.descricao())
                    )
                    .verifyComplete();

            verify(produtoOutputPort, times(1)).findById(id);
            verify(produtoOutputPort, times(1)).save(any());
            verifyNoMoreInteractions(produtoOutputPort);
        }

        @Test
        void shouldThrowExceptionWhenProdutoNotFound() {
            // Arrange
            Long id = 987456321L;
            var update = ProdutoFactory.buildUpdateProduto();
            var domainWithId = ProdutoFactory.buildProdutoWithId(id);

            when(produtoOutputPort.findById(id))
                    .thenReturn(Mono.error(new ProdutoNotFoundException(
                            PRODUTO_NOT_FOUND.params(id.toString()).getMessage())));

            // Act
            Mono<ProdutoResponse> result = produtoService.atualizar(id, update);

            // Assert
            StepVerifier.create(result)
                    .expectErrorMatches(throwable -> throwable instanceof ProdutoNotFoundException &&
                            throwable.getMessage().equals(PRODUTO_NOT_FOUND
                                    .params(domainWithId.id().toString()).getMessage()))
                    .verify();

            verify(produtoOutputPort, times(1)).findById(id);
            verifyNoMoreInteractions(produtoOutputPort);
        }
    }

    @Nested
    class DeletandoProduto {

        @Test
        void shouldDeleteProdutoSuccessfully() {
            // Arrange
            Long id = 987456321L;

            when(produtoOutputPort.findById(id))
                .thenReturn(Mono.just(domain));
            when(produtoOutputPort.delete(any()))
                .thenReturn(Mono.empty());

            // Act
            Mono<Void> deleteProduct = produtoService.excluir(id);

            // Assert
            StepVerifier.create(deleteProduct)
                    .verifyComplete();

            verify(produtoOutputPort, times(1)).findById(id);
            verify(produtoOutputPort, times(1)).delete(any());
            verifyNoMoreInteractions(produtoOutputPort);
        }

        @Test
        void shouldThrowExceptionWhenProdutoNotFound() {
            // Arrange
            Long id = 987456321L;

            when(produtoOutputPort.findById(id))
                    .thenReturn(Mono.error(new ProdutoNotFoundException(
                            PRODUTO_NOT_FOUND.params(id.toString()).getMessage())));
            // Act
            Mono<Void> deleteProduct = produtoService.excluir(id);

            // Assert
            StepVerifier.create(deleteProduct)
                    .expectErrorMatches(throwable -> throwable instanceof ProdutoNotFoundException &&
                            throwable.getMessage().equals(PRODUTO_NOT_FOUND.params(domain.id().toString()).getMessage()))
                    .verify();

            verify(produtoOutputPort, times(1)).findById(id);
            verifyNoMoreInteractions(produtoOutputPort);
        }
    }
}