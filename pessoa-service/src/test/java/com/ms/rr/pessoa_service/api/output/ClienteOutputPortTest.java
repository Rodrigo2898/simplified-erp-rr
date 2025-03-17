package com.ms.rr.pessoa_service.api.output;

import com.ms.rr.pessoa_service.application.port.output.ClienteOutputPort;
import com.ms.rr.pessoa_service.domain.model.ClienteDomain;
import com.ms.rr.pessoa_service.domain.model.EnderecoDomain;
import com.ms.rr.pessoa_service.factory.CreateClienteDomainFactory;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@Transactional
public abstract class ClienteOutputPortTest {
    public abstract ClienteOutputPort getClienteOutputPort();

    @Test
    void save() {
        ClienteDomain clienteDomain = new ClienteDomain(
                null,
                "João da Silva",
                "joao@email.com",
                "11999999999",
                "123.456.789-00",
                LocalDate.now(),
                List.of(
                        new EnderecoDomain(null, "Rua A", "Cidade X", "SP", "12345-678"),
                        new EnderecoDomain(null, "Rua B", "Cidade Y", "RJ", "98765-432")
                )
        );
        getClienteOutputPort().save(clienteDomain);

        // Verifique se o Endereco foi salvo com pessoa_id não nulo
        Optional<ClienteDomain> result = getClienteOutputPort().findById(clienteDomain.getId());
        assertTrue(result.isPresent());
        assertEquals(clienteDomain, result.get());
    }
}
