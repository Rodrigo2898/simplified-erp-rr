package com.ms.rr.pessoa_service.api.output;

import com.ms.rr.pessoa_service.adapter.output.persistence.entity.Cliente;
import com.ms.rr.pessoa_service.domain.port.output.ClienteOutputPort;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.factory.ClienteDomainFactory;
import com.ms.rr.pessoa_service.infrastructure.adapter.AbstractContainerTest;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public abstract class ClienteOutputPortTest extends AbstractContainerTest {
    public abstract ClienteOutputPort getClienteOutputPort();

    @Autowired
    TestEntityManager testEntityManager;

    ClienteDomain clienteDomain;

    @BeforeEach
    void setUp() {
        clienteDomain = ClienteDomainFactory.createClienteDomain();
    }

    @Test
    void save() {

        getClienteOutputPort().save(clienteDomain);

        Cliente clienteSalvo = testEntityManager.find(Cliente.class, clienteDomain.id());

        assertNotNull(clienteSalvo);
        assertEquals("Vegeta", clienteSalvo.getNome());
    }

    @Test
    void findById() {
        testEntityManager.persistAndFlush(Cliente.fromDomain(clienteDomain));
        testEntityManager.clear();

        Optional<ClienteDomain> clienteRecuperado = getClienteOutputPort().findById(clienteDomain.id());

        assertTrue(clienteRecuperado.isPresent());
        assertEquals("Vegeta", clienteRecuperado.get().nome());
        assertThat(clienteRecuperado).contains(clienteDomain);
    }

    @Test
    void findAll() {
        ClienteDomain clienteDomain1 = Instancio.of(ClienteDomain.class)
                .set(Select.field("cpf"), "975.771.250-75")
                .set(Select.field("email"), "test1@gmail.com")
                .set(Select.field("telefone"), "(61) 25517852")
                .create();
        ClienteDomain clienteDomain2 = Instancio.of(ClienteDomain.class)
                .set(Select.field("cpf"), "427.215.120-72")
                .set(Select.field("email"), "test2@gmail.com")
                .set(Select.field("telefone"), "(61) 37491474")
                .create();

        List<ClienteDomain> clientes = List.of(clienteDomain1, clienteDomain2);

        clientes
                .forEach(clienteDomain -> {
                    testEntityManager.persistAndFlush(Cliente.fromDomain(clienteDomain));
                    testEntityManager.clear();
                });

        List<ClienteDomain> clientesRecupreados = getClienteOutputPort().findAll();

        assertEquals(clientes.size(), clientesRecupreados.size());
    }

    @Test
    void delete() {
        Cliente cliente = Cliente.fromDomain(clienteDomain);

        testEntityManager.persistAndFlush(cliente);
        testEntityManager.clear();

        getClienteOutputPort().delete(clienteDomain);

        Cliente clienteRemovido = testEntityManager.find(Cliente.class, cliente.getId());

        assertNull(clienteRemovido);
    }
}
