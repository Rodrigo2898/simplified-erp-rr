package com.ms.rr.pessoa_service.api.output;

import com.ms.rr.pessoa_service.domain.port.output.ClienteOutputPort;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.domain.query.ClienteQuery;
import com.ms.rr.pessoa_service.factory.CreateClienteDomainFactory;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public abstract class ClienteOutputPortTest {
    public abstract ClienteOutputPort getClienteOutputPort();

    @Test
    void save() {
        ClienteDomain clienteDomain = CreateClienteDomainFactory.buildWithOneItem();
        getClienteOutputPort().save(clienteDomain);

        Optional<ClienteDomain> result = getClienteOutputPort().findById(clienteDomain.id());

        assertTrue(result.isPresent());
        assertEquals(clienteDomain, result.get());
    }

    @Test
    void findAll() {
        List<ClienteDomain> clienteDomainList = Instancio.ofList(ClienteDomain.class)
                .size(20)
                .create();

        getClienteOutputPort().save(clienteDomainList);

        List<ClienteDomain> result = getClienteOutputPort().findAll();

        assertEquals(clienteDomainList.size(), result.size());
    }

    @Test
    void findByNome() {
        ClienteDomain clienteDomain1 = Instancio.of(ClienteDomain.class)
                .set(Select.field("cpf"), "338.061.710-50")
                .set(Select.field("email"), "test@example.com")
                .create();
        ClienteDomain clienteDomain2 = Instancio.of(ClienteDomain.class)
                .set(Select.field("nome"), "Rodrigo Feitosa")
                .set(Select.field("cpf"), "123.456.789-09")
                .set(Select.field("email"), "test2@example.com")
                .create();

        ClienteQuery query = new ClienteQuery.Builder().nome("ROD").build();

        getClienteOutputPort().save(List.of(clienteDomain1, clienteDomain2));

        List<ClienteDomain> result = getClienteOutputPort().find(query);

        assertEquals(1, result.size());
        assertEquals(clienteDomain2, result.get(0));
    }

    @Test
    void findByCpf() {
        ClienteDomain clienteDomain1 = Instancio.of(ClienteDomain.class)
                .set(Select.field("cpf"), "338.061.710-50")
                .set(Select.field("email"), "test@example.com")
                .create();
        ClienteDomain clienteDomain2 = Instancio.of(ClienteDomain.class)
                .set(Select.field("cpf"), "123.456.789-09")
                .set(Select.field("email"), "test2@example.com")
                .create();

        ClienteQuery query = new ClienteQuery.Builder().cpf("123.456.789-09").build();

        getClienteOutputPort().save(List.of(clienteDomain1, clienteDomain2));

        List<ClienteDomain> result = getClienteOutputPort().find(query);

        assertEquals(1, result.size());
        assertEquals(clienteDomain2, result.get(0));
    }

    @Test
    void delete() {
        ClienteDomain clienteDomain = Instancio.create(ClienteDomain.class);

        getClienteOutputPort().save(clienteDomain);

        Optional<ClienteDomain> clienteSalvo = getClienteOutputPort().findById(clienteDomain.id());
        assertTrue(clienteSalvo.isPresent());

        getClienteOutputPort().deleteById(clienteDomain.id());
        Optional<ClienteDomain> clienteDeletado = getClienteOutputPort().findById(clienteDomain.id());
        assertFalse(clienteDeletado.isPresent());
    }
}
