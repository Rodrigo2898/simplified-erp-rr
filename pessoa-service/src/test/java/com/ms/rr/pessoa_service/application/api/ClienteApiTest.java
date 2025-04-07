package com.ms.rr.pessoa_service.application.api;

import com.ms.rr.pessoa_service.application.dto.in.CreateCliente;
import com.ms.rr.pessoa_service.application.dto.in.UpdateCliente;
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

import java.util.Random;

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
        void shouldCallCreateClienteUseCase() {
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

    @Nested
    class FindClienteById {

        @Test
        void shouldCallFindByIdClienteUseCase() {
            // Arrange
            var id = new Random().nextLong();
            var cliente = Instancio.create(ClienteDomain.class);
            doReturn(cliente).when(clienteUseCase).buscarPorId(id);

            // Act
            var response = clienteApi.findById(id);

            // Assert
            verify(clienteUseCase, times(1)).buscarPorId(id);
        }

        @Test
        void shouldFindByIdSuccessfully() {
            // Arrange
            var id = new Random().nextLong();
            var cliente = Instancio.create(ClienteDomain.class);
            doReturn(cliente).when(clienteUseCase).buscarPorId(id);

            // Act
            var response = clienteApi.findById(id);
            var clienteResponse = ClienteResponse.fromDomain(cliente);

            // Assert
            verify(clienteUseCase).buscarPorId(id);
            verifyNoMoreInteractions(clienteUseCase);

            assertNotNull(response);
            assertEquals(clienteResponse, response);
        }
    }

    @Nested
    class Update {

        @Test
        void shouldCallUpdateClienteUseCase() {
            // Arrange
            var id = new Random().nextLong();
            var dto = Instancio.create(UpdateCliente.class);
            var cliente = dto.toDomain(id);

            ArgumentCaptor<ClienteDomain> captor = ArgumentCaptor.forClass(ClienteDomain.class);

            doReturn(cliente).when(clienteUseCase).buscarPorId(id);

            // Act
            var response = clienteApi.update(id, dto);

            // Assert
            verify(clienteUseCase, times(1)).buscarPorId(id);
            verify(clienteUseCase, times(1)).salvar(captor.capture());
        }

        @Test
        void shouldUpdateClienteSuccessfully() {
            // Arrange
            var id = new Random().nextLong();
            var dto = Instancio.create(UpdateCliente.class);
            var cliente = dto.toDomain(id);

            ArgumentCaptor<ClienteDomain> captor = ArgumentCaptor.forClass(ClienteDomain.class);
            doReturn(cliente).when(clienteUseCase).buscarPorId(id);

            // Act
            var response = clienteApi.update(id, dto);

            // Assert
            verify(clienteUseCase).salvar(captor.capture());
            verify(clienteUseCase).buscarPorId(id);
            verifyNoMoreInteractions(clienteUseCase);

            assertEquals(ClienteResponse.fromDomain(cliente), response);
        }
    }

    @Nested
    class DeleteCliente {

        @Test
        void shouldCallDeleteClienteUseCase() {
            // Arrange
            var id = new Random().nextLong();

            // Act
            clienteApi.delete(id);

            // Assert
            verify(clienteUseCase, times(1)).excluir(id);
        }

        @Test
        void shouldDeleteSuccessfully() {
            // Arrange
            var id = new Random().nextLong();

            // Act
            clienteApi.delete(id);

            // Assert
            verify(clienteUseCase).excluir(id);
            verifyNoMoreInteractions(clienteUseCase);
        }
    }
}