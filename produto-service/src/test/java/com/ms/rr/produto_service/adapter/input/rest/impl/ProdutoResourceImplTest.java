package com.ms.rr.produto_service.adapter.input.rest.impl;

import com.ms.rr.produto_service.domain.dto.in.CreateProduto;
import com.ms.rr.produto_service.domain.port.input.ProdutoUseCase;
import com.ms.rr.produto_service.factory.ProdutoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class ProdutoResourceImplTest {

    @Mock
    private ProdutoUseCase produtoUseCase;

    @InjectMocks
    private ProdutoResourceImpl produtoResource;

    @Nested
    class CreateProdutoResource {

        @Test
        void shouldCreateProdutoSuccessfully() {
            // Arrange
            var createProduto = ProdutoFactory.createProduto();
            var produtoResponse = ProdutoFactory.buildProdutoResponse();

            when(produtoUseCase.salvar(any())).thenReturn(Mono.just(produtoResponse));

            // Act
            var response = produtoResource.create(createProduto);

            // Assert
            StepVerifier.create(response)
                    .expectNext(produtoResponse)
                    .verifyComplete();

            verify(produtoUseCase).salvar(createProduto);
        }
    }

}