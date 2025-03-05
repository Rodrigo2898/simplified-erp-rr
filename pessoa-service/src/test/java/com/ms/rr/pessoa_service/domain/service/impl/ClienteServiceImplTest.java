package com.ms.rr.pessoa_service.domain.service.impl;

import com.ms.rr.pessoa_service.application.port.output.ClienteOutputPort;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.factory.CreateClienteDomainFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    ClienteOutputPort clienteOutputPort;

    @InjectMocks
    ClienteServiceImpl clienteService;

    private ClienteDomain cliente;

    @BeforeEach
    void setUp() {
        cliente = CreateClienteDomainFactory.buildWithOneItem();
    }

    @Nested
    class SaveCliente {

        @Test
        void shouldCallSaveClienteOutputPort() {
            // ACT
            clienteService.salvar(cliente);

            // ASSERT
            verify(clienteOutputPort, times(1)).save(any());
        }

        @Test
        void shouldSaveClienteOutputPort() {
            // ACT
            clienteService.salvar(cliente);

            // ASSERT
            verify(clienteOutputPort).save(cliente);
            verifyNoMoreInteractions(clienteOutputPort);
        }
    }

    @Nested
    class FindClienteById {

        @Test
        void shouldReturnClienteWhenExistst() {
            // ARRANGE
            when(clienteOutputPort.findById(cliente.getId())).thenReturn(Optional.of(cliente));

            // ACT
            var resultado = clienteService.buscarPorId(cliente.getId());

            // ASSERT
            assertTrue(resultado.isPresent());
            assertEquals(cliente.getId(), resultado.get().getId());
            verify(clienteOutputPort, times(1)).findById(cliente.getId());
            verifyNoMoreInteractions(clienteOutputPort);
        }

        @Test
        void shouldReturnClienteWhenNotExistst() {
            // ARRANGE
            when(clienteOutputPort.findById(cliente.getId())).thenReturn(Optional.empty());

            // ACT & ASSERT
            assertThrows(NoSuchElementException.class, () -> clienteService.buscarPorId(cliente.getId()));

            verify(clienteOutputPort).findById(cliente.getId());
            verifyNoMoreInteractions(clienteOutputPort);
        }
    }

    @Nested
    class FindAllClientes {

        @Test
        void shouldReturnListOfClientes() {
            // ARRANGE
            List<ClienteDomain> clientes = List.of(cliente);
            when(clienteOutputPort.findAll()).thenReturn(clientes);

            // ACT
            var resultado = clienteService.buscarTodos();

            // ASSERT
            assertFalse(resultado.isEmpty());
            assertEquals(resultado, clientes);
            assertEquals(resultado.size(), clientes.size());
            verify(clienteOutputPort, times(1)).findAll();
            verifyNoMoreInteractions(clienteOutputPort);
        }

        @Test
        void shouldReturnEmptyListWhenListIsEmpty() {
            // ARRANGE
            when(clienteOutputPort.findAll()).thenReturn(List.of());

            // ACT
            var resultado = clienteService.buscarTodos();

            assertTrue(resultado.isEmpty());
            assertEquals(0, resultado.size());
            verify(clienteOutputPort, times(1)).findAll();
            verifyNoMoreInteractions(clienteOutputPort);
        }
    }

    @Nested
    class DeleteCliente {

        @Test
        void shouldCallDeleteClienteById() {
            // ARRANGE
            var clienteId = new Random().nextLong();

            // ACT
            clienteService.excluir(clienteId);

            // ASSERT
            verify(clienteOutputPort, times(1)).deleteById(clienteId);
            verifyNoMoreInteractions(clienteOutputPort);
        }
    }
}