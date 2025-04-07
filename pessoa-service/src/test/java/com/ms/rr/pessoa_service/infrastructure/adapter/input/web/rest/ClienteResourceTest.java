package com.ms.rr.pessoa_service.infrastructure.adapter.input.web.rest;

import com.ms.rr.pessoa_service.application.api.ClienteApi;
import com.ms.rr.pessoa_service.application.dto.in.CreateCliente;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClienteResourceTest {

    @Mock
    private ClienteApi clienteApi;

    @InjectMocks
    private ClienteResource clienteResource;

    @Captor
    ArgumentCaptor<CreateCliente> createClienteArgumentCaptor;

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
        void shouldPassCorrectParametersToClienteApi() {
            // Arrange
            var in = Instancio.create(CreateCliente.class);

            // Act
            var response = clienteResource.create(in);

            // Assert
            verify(clienteApi).create(createClienteArgumentCaptor.capture());
            assertEquals(in, createClienteArgumentCaptor.getValue());
        }

        @Test
        void shouldReturnResponseBodyCorrectly() {
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
}