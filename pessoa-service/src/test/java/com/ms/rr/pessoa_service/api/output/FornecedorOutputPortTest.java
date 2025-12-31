package com.ms.rr.pessoa_service.api.output;

import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;
import com.ms.rr.pessoa_service.domain.port.output.FornecedorOutputPort;
import com.ms.rr.pessoa_service.factory.FornecedorDomainFactory;
import com.ms.rr.pessoa_service.infrastructure.adapter.AbstractContainerTest;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class FornecedorOutputPortTest extends AbstractContainerTest {

    public abstract FornecedorOutputPort getFornecedorOutputPort();

    @Test
    void save() {
        FornecedorDomain fornecedorDomain = FornecedorDomainFactory.createFornecedorDomain();

        getFornecedorOutputPort().save(fornecedorDomain);

        Optional<FornecedorDomain> fornecedor = getFornecedorOutputPort()
                .findAll()
                .stream().findFirst();

        assertTrue(fornecedor.isPresent());
        assertEquals("Goku", fornecedor.get().nome());
        assertEquals("goku@gmail.com", fornecedor.get().email());
    }

    @Test
    void findByCnpj() {
        FornecedorDomain fornecedorDomain1 = FornecedorDomainFactory.createFornecedorDomain();

        getFornecedorOutputPort().save(fornecedorDomain1);

        FornecedorDomain fornecedor = getFornecedorOutputPort().findFornecedorByCnpj(fornecedorDomain1.cnpj());

        assertEquals("50.095.037/0001-19", fornecedor.cnpj());;
    }
}
