package com.ms.rr.pessoa_service.api.output;

import com.ms.rr.pessoa_service.adapter.output.persistence.entity.Fornecedor;
import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;
import com.ms.rr.pessoa_service.domain.port.output.FornecedorOutputPort;
import com.ms.rr.pessoa_service.factory.FornecedorDomainFactory;
import com.ms.rr.pessoa_service.infrastructure.adapter.AbstractContainerTest;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public abstract class FornecedorOutputPortTest extends AbstractContainerTest {

    public abstract FornecedorOutputPort getFornecedorOutputPort();

    @Autowired
    TestEntityManager testEntityManager;

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

        testEntityManager.persist(Fornecedor.fromDomain(fornecedorDomain1));

        FornecedorDomain fornecedorRecuperado = getFornecedorOutputPort().findFornecedorByCnpj(fornecedorDomain1.cnpj());

        assertEquals("50.095.037/0001-19", fornecedorRecuperado.cnpj());
        assertNotNull(fornecedorRecuperado);
    }

    @Test
    void findById() {
        FornecedorDomain fornecedorDomain = FornecedorDomainFactory.createFornecedorDomain();

        testEntityManager.persist(Fornecedor.fromDomain(fornecedorDomain));

        Optional<FornecedorDomain> fornecedorRecuperado = getFornecedorOutputPort().findById(fornecedorDomain.id());

        assertTrue(fornecedorRecuperado.isPresent());
        assertEquals("Goku", fornecedorRecuperado.get().nome());
        assertThat(fornecedorRecuperado).contains(fornecedorDomain);
    }

    @Test
    void findAll() {
        FornecedorDomain fornecedorDomain1 = Instancio.of(FornecedorDomain.class)
                .set(Select.field("cnpj"), "02.925.356/0001-61")
                .set(Select.field("email"), "test1@gmail.com")
                .set(Select.field("telefone"), "(61) 25517852")
                .create();
        FornecedorDomain fornecedorDomain2 = Instancio.of(FornecedorDomain.class)
                .set(Select.field("cnpj"), "58.192.226/0001-11")
                .set(Select.field("email"), "test2@gmail.com")
                .set(Select.field("telefone"), "(61) 37491474")
                .create();

        List<FornecedorDomain> fornecedores = List.of(fornecedorDomain1, fornecedorDomain2);

        fornecedores
                .forEach(fornecedorDomain ->
                        testEntityManager.persist(Fornecedor.fromDomain(fornecedorDomain)));

        List<FornecedorDomain> fornecedoreRecupreados = getFornecedorOutputPort().findAll();

        assertEquals(fornecedores.size(), fornecedoreRecupreados.size());
    }
}
