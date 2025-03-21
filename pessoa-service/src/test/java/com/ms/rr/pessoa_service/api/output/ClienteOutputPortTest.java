package com.ms.rr.pessoa_service.api.output;

import com.ms.rr.pessoa_service.application.port.output.ClienteOutputPort;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.factory.CreateClienteDomainFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public abstract class ClienteOutputPortTest {
    public abstract ClienteOutputPort getClienteOutputPort();

    @Test
    void save() {
        ClienteDomain clienteDomain = CreateClienteDomainFactory.buildWithOneItem();
        getClienteOutputPort().save(clienteDomain);

        // Verifique se o Endereco foi salvo com pessoa_id não nulo
        Optional<ClienteDomain> result = getClienteOutputPort().findById(clienteDomain.getId());

        assertTrue(result.isPresent());
        assertEquals(clienteDomain.getId(), result.get().getId());
        assertEquals(clienteDomain.getNome(), result.get().getNome());
        assertEquals(clienteDomain.getTelefone(), result.get().getTelefone());
        assertEquals(clienteDomain.getEmail(), result.get().getEmail());
        assertEquals(clienteDomain.getId(), result.get().getId());
        assertEquals(clienteDomain.getCpf(), result.get().getCpf());
        assertEquals(clienteDomain.getDataCadastro(), result.get().getDataCadastro());
    }
}
