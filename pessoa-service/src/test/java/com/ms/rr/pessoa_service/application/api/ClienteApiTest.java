package com.ms.rr.pessoa_service.application.api;

import com.ms.rr.pessoa_service.application.dto.in.CreateCliente;
import com.ms.rr.pessoa_service.application.dto.out.ClienteResponse;
import com.ms.rr.pessoa_service.application.port.input.ClienteUseCase;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import org.instancio.Instancio;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteApiTest {

    @Mock
    ClienteUseCase clienteUseCase;

    @InjectMocks
    ClienteApi clienteApi;

    @Nested
    class Create {

        @Test
        void shouldCallClienteUseCase() {
            // Arrange
            CreateCliente dto = Instancio.create(CreateCliente.class);
            ArgumentCaptor<ClienteDomain> captor = ArgumentCaptor.forClass(ClienteDomain.class);

            // Act
            clienteApi.create(dto);

            // Assert
            verify(clienteUseCase, times(1)).salvar(captor.capture());
        }

        @Test
        void shouldCreateClienteSuccessfully() {
            // Arrange
            CreateCliente dto = Instancio.create(CreateCliente.class);
            ArgumentCaptor<ClienteDomain> captor = ArgumentCaptor.forClass(ClienteDomain.class);

            // Act
            clienteApi.create(dto);

            // Assert
            verify(clienteUseCase).salvar(captor.capture());
            verifyNoMoreInteractions(clienteUseCase);

            ClienteDomain cliente = captor.getValue();

            assertNotNull(cliente);
            assertEquals(cliente.nome(), dto.nome());
            assertEquals(cliente.email(), dto.email());
            assertEquals(cliente.cpf(), dto.cpf());
            assertEquals(cliente.telefone(), dto.telefone());
            assertEquals(cliente.dataCadastro(), dto.dataCadastro());
        }
    }

    @Nested
    class ListClientes {

        @Test
        void shouldCallClienteUseCaseList() {
            // Arrange
            var clientes = Instancio.stream(ClienteDomain.class).limit(10).toList();
            doReturn(clientes).when(clienteUseCase).buscarTodos();

            // Act
            var response = clienteApi.list();

            // Assert
            verify(clienteUseCase, times(1)).buscarTodos();
        }

        @Test
        void shouldListClienteSuccessfully() {
            // Arrange
            var clientes = Instancio.stream(ClienteDomain.class).limit(10).toList();
            doReturn(clientes).when(clienteUseCase).buscarTodos();

            // Act
            var response = clienteApi.list();

            // Assert
            verify(clienteUseCase).buscarTodos();
            verifyNoMoreInteractions(clienteUseCase);

            assertNotNull(response);
            assertEquals(clientes.size(), response.size());
            assertEquals(clientes.stream()
                    .map(ClienteResponse::fromDomain)
                    .toList(), response);
        }
    }
}