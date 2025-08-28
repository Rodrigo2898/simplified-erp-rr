package com.ms.rr.estoque_service.domain.service;

import com.ms.rr.estoque_service.domain.dto.in.CreateEstoque;
import com.ms.rr.estoque_service.domain.model.EstoqueDomain;
import com.ms.rr.estoque_service.domain.port.output.EstoqueOutputPort;
import com.ms.rr.estoque_service.factory.EstoqueFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    class SaveInEstoque {

        @Test
        void shouldSaveEstoqueSuccessfullyWhenProdutoIsFound() {
            var estoqueCreated = createEstoque;
            var nomeProduto = "Camisa Chelsea";

            when(estoqueOutputPort.findByNomeProduto(nomeProduto))
                    .thenReturn(Optional.of(estoqueDomain));
            
            estoqueService.salvar(estoqueCreated);

            verify(estoqueOutputPort).save(any(EstoqueDomain.class));

        }

        @Test
        void shouldSaveEstoqueSuccessfullyWhenProdutoIsNotFound() {
            var estoqueCreated = createEstoque;
            var nomeProduto = "Camisa Chelsea";

            when(estoqueOutputPort.findByNomeProduto(nomeProduto))
                    .thenReturn(Optional.empty());

            estoqueService.salvar(estoqueCreated);

            verify(estoqueOutputPort).save(any(EstoqueDomain.class));
        }
    }
}