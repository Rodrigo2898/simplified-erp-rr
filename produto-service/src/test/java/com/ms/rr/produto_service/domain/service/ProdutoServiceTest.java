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
            //ARRANGE
            when(fornecedorOutputPort.findFornecedorById(anyLong()))
                    .thenReturn(Mono.just(fornecedorDomain));
            when(produtoOutputPort.save(any(ProdutoDomain.class)))
                    .thenReturn(Mono.just(createProduto.toDomain()));

            // ACT
            StepVerifier.create(produtoService.salvar(createProduto))
                    .expectNextMatches(produtoResponse -> produtoResponse.nome().equals(createProduto.toDomain().nome()))
                    .verifyComplete();

            // ASSERT
            verify(fornecedorOutputPort).findFornecedorById(createProduto.fornecedorId());
            verify(produtoOutputPort).save(any(ProdutoDomain.class));
        }

        @Test
        void shouldThrowExceptionWhenFornecedorNotFound() {
            // ARRANGE
            when(fornecedorOutputPort.findFornecedorById(anyLong()))
                    .thenReturn(Mono.error(new FornecedorNotFoundException(
                            FORNECEDOR_NOT_FOUND.params(createProduto.fornecedorId().toString()).getMessage())));

            // ACT & ASSERT
            StepVerifier.create(produtoService.salvar(createProduto))
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
            // ARRANGE
            var id = 987456321L;
            var response = ProdutoFactory.buildProdutoResponse();

            when(produtoOutputPort.findById(id))
                    .thenReturn(Mono.just(domain));

            // ACT
            StepVerifier.create(produtoService.buscarPorId(id))
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

            StepVerifier.create(produtoService.buscarPorId(id))
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
            var produto1 = ProdutoFactory.buildProdutoWithId(1L);
            var produto2 = ProdutoFactory.buildProdutoWithId(2L);
            
            when(produtoOutputPort.findAll())
                    .thenReturn(Flux.just(produto1, produto2));

            StepVerifier.create(produtoService.buscarTodosProdutos())
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
            String categoria = "Roupas";
            var produto1 = ProdutoFactory.buildProdutoWithCategoria(categoria);
            var produto2 = ProdutoFactory.buildProdutoWithCategoria(categoria);
            when(produtoOutputPort.findAllByCategoria(categoria))
                    .thenReturn(Flux.just(produto1, produto2));

            StepVerifier.create(produtoService.buscarProdutosPorCategoria(categoria))
                    .expectNext(ProdutoResponse.fromDomain(produto1))
                    .expectNext(ProdutoResponse.fromDomain(produto2))
                    .verifyComplete();

            verify(produtoOutputPort, times(1)).findAllByCategoria(categoria);
            verifyNoMoreInteractions(produtoOutputPort);
        }

        @Test
        void shouldThrowExceptionWhenCategoriaNotFound() {}
    }
}