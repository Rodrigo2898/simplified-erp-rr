package com.ms.rr.pessoa_service.api.output;

import com.ms.rr.pessoa_service.application.port.output.ClienteOutputPort;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.factory.CreateClienteDomainFactory;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@Transactional
public abstract class ClienteOutputPortTest {
    public abstract ClienteOutputPort getClienteOutputPort();

    @Test
    void save() {
        ClienteDomain clienteDomain = CreateClienteDomainFactory.buildWithOneItem();

        getClienteOutputPort().save(clienteDomain);

        Optional<ClienteDomain> result = getClienteOutputPort().findById(clienteDomain.getId());

        assertTrue(result.isPresent());
        assertEquals(clienteDomain, result.get());
    }
}
