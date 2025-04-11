package com.ms.rr.pessoa_service.infrastructure.adapter.input.web.rest;

import com.ms.rr.pessoa_service.application.api.ClienteApi;
import com.ms.rr.pessoa_service.application.dto.in.CreateCliente;
import com.ms.rr.pessoa_service.application.dto.in.UpdateCliente;
import com.ms.rr.pessoa_service.application.dto.out.ClienteResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteResourceTest {

    @Mock
    private ClienteApi clienteApi;

    @InjectMocks
    private ClienteResource clienteResource;

    @Captor
    ArgumentCaptor<Long> clienteIdArgumentCaptor;

    @Captor
    ArgumentCaptor<CreateCliente> createClienteArgumentCaptor;

    @Captor
    ArgumentCaptor<UpdateCliente> updateClienteArgumentCaptor;

    @Nested
    class CreateClienteResource {

        @Test
        void shouldReturnHttpCreated() {
            // Arrange
            var in = Instancio.create(CreateCliente.class);

            // Act
            var response = clienteResource.create(in);

            // Assert
            assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        }

        @Test
        void shouldPassCorrectParametersToClienteApiCreate() {
            // Arrange
            var in = Instancio.create(CreateCliente.class);

            // Act
            var response = clienteResource.create(in);

            // Assert
            verify(clienteApi).create(createClienteArgumentCaptor.capture());
            assertEquals(in, createClienteArgumentCaptor.getValue());
        }

        @Test
        void shouldReturnCreateResponseBodyCorrectly() {
            // Arrange
            var in = Instancio.create(CreateCliente.class);

            // Act
            var response = clienteResource.create(in);

            // Assert
            assertNotNull(response);
            assertNotNull(response.getBody());

            assertEquals(in.nome(), response.getBody().nome());
            assertEquals(in.email(), response.getBody().email());
            assertEquals(in.cpf(), response.getBody().cpf());
            assertEquals(in.telefone(), response.getBody().telefone());
            assertEquals(in.dataCadastro(), response.getBody().dataCadastro());
        }
    }

    @Nested
    class GetAllClienteResource {

        @Test
        void shouldReturnHttpOk() {
            // Arrange
            var out = Instancio.stream(ClienteResponse.class).limit(10).toList();
            doReturn(out).when(clienteApi).list();

            // Act
            var response = clienteResource.getAll();

            // Assert
            assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        }

        @Test
        void shouldReturnListOfClientesCorrectly() {
            // Arrange
            var out = Instancio.stream(ClienteResponse.class).limit(10).toList();
            doReturn(out).when(clienteApi).list();

            // Act
            var response = clienteResource.getAll();

            // Assert
            assertNotNull(response);
            assertNotNull(response.getBody());

            assertEquals(out.size(), response.getBody().size());
            assertEquals(out, response.getBody());
        }
    }

    @Nested
    class GetClienteByIdResource {

        @Test
        void shouldReturnHttpOk() {
            // Arrange
            var id = new Random().nextLong();
            var out = Instancio.create(ClienteResponse.class);
            doReturn(out).when(clienteApi).findById(id);

            // Act
            var response = clienteResource.getById(id);

            // Assert
            assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        }

        @Test
        void shouldPassCorrectParametersToClienteApiGetById() {
            // Arrange
            var id = new Random().nextLong();
            var out = Instancio.create(ClienteResponse.class);
            doReturn(out).when(clienteApi).findById(id);

            // Act
            var response = clienteResource.getById(id);

            // Assert
            verify(clienteApi).findById(clienteIdArgumentCaptor.capture());
            assertEquals(id, clienteIdArgumentCaptor.getValue());
        }

        @Test
        void shouldReturnFindByIdResponseBodyCorrectly() {
            // Arrange
            var id = new Random().nextLong();
            var out = Instancio.create(ClienteResponse.class);
            doReturn(out).when(clienteApi).findById(id);

            // Act
            var response = clienteResource.getById(id);

            // Assert
            assertNotNull(response);
            assertNotNull(response.getBody());

            assertEquals(out.id(), response.getBody().id());
            assertEquals(out.nome(), response.getBody().nome());
            assertEquals(out.email(), response.getBody().email());
            assertEquals(out.telefone(), response.getBody().telefone());
            assertEquals(out.cpf(), response.getBody().cpf());
            assertEquals(out.dataCadastro(), response.getBody().dataCadastro());
        }
    }

    @Nested
    class UpdateClienteResource {

        @Test
        void shouldReturnHttpOk() {
            // Arrange
            var id = new Random().nextLong();
            var in = Instancio.create(UpdateCliente.class);
            var out = Instancio.create(ClienteResponse.class);
            doReturn(out).when(clienteApi).update(id, in);

            // Act
            var response = clienteResource.update(id, in);

            // Assert
            assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        }

        @Test
        void shouldPassCorrectParametersToClienteApiUpdate() {
            // Arrange
            var id = new Random().nextLong();
            var in = Instancio.create(UpdateCliente.class);
            var out = Instancio.create(ClienteResponse.class);
            doReturn(out).when(clienteApi).update(id, in);

            // Act
            var response = clienteResource.update(id, in);

            // Assert
            verify(clienteApi).update(clienteIdArgumentCaptor.capture(), updateClienteArgumentCaptor.capture());
            assertEquals(id, clienteIdArgumentCaptor.getValue());
            assertEquals(in, updateClienteArgumentCaptor.getValue());
        }

        @Test
        void shouldReturnUpdateResponseBodyCorrectly() {
            // Arrange
            var id = new Random().nextLong();
            var in = Instancio.create(UpdateCliente.class);
            var out = Instancio.create(ClienteResponse.class);
            doReturn(out).when(clienteApi).update(id, in);

            // Act
            var response = clienteResource.update(id, in);

            // Assert
            assertNotNull(response);
            assertNotNull(response.getBody());

            assertEquals(out.id(), response.getBody().id());
            assertEquals(out.nome(), response.getBody().nome());
            assertEquals(out.email(), response.getBody().email());
            assertEquals(out.telefone(), response.getBody().telefone());
            assertEquals(out.cpf(), response.getBody().cpf());
            assertEquals(out.dataCadastro(), response.getBody().dataCadastro());
        }
    }

    @Nested
    class DeleteClienteResource {

        @Test
        void shouldReturnHttpNoContent() {
            // Arrange
            var id = new Random().nextLong();
            doNothing().when(clienteApi).delete(id);

            // Act
            ResponseEntity<Void> response = clienteResource.delete(id);

            // Arrange
            assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        }

        @Test
        void shouldPassCorrectParametersToClienteApiDelete() {
            // Arrange
            var id = new Random().nextLong();
            doNothing().when(clienteApi).delete(id);

            // Act
            ResponseEntity<Void> response = clienteResource.delete(id);

            // Assert
            verify(clienteApi).delete(clienteIdArgumentCaptor.capture());
            assertEquals(id, clienteIdArgumentCaptor.getValue());
        }
    }
}