package com.ms.rr.produto_service.domain.service;

import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.exception.FornecedorNotFoundException;
import com.ms.rr.produto_service.domain.model.FornecedorDomain;
import com.ms.rr.produto_service.domain.model.ProdutoDomain;
import com.ms.rr.produto_service.domain.port.output.FornecedorOutputPort;
import com.ms.rr.produto_service.domain.port.output.ProdutoOutputPort;
import com.ms.rr.produto_service.factory.CreateFornecedorFactory;
import com.ms.rr.produto_service.factory.CreateProdutoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;

import static com.ms.rr.produto_service.domain.exception.BaseErrorMessage.FORNECEDOR_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoOutputPort produtoOutputPort;

    @Mock
    private FornecedorOutputPort fornecedorOutputPort;

    @InjectMocks
    private ProdutoService produtoService;

    private CreateProduto createProduto;
    private FornecedorDomain fornecedorDomain;

    @BeforeEach
    void setUp() {
        createProduto = CreateProdutoFactory.createProduto();
        fornecedorDomain = CreateFornecedorFactory.createFornecedor();
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
}