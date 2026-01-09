package com.ms.rr.pessoa_service.api.output;

import com.ms.rr.pessoa_service.adapter.output.persistence.entity.Fornecedor;
import com.ms.rr.pessoa_service.domain.model.FornecedorDomain;
import com.ms.rr.pessoa_service.domain.port.output.FornecedorOutputPort;
import com.ms.rr.pessoa_service.factory.EnderecoDomainFactory;
import com.ms.rr.pessoa_service.factory.FornecedorDomainFactory;
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

public abstract class FornecedorOutputPortTest extends AbstractContainerTest {

    public abstract FornecedorOutputPort getFornecedorOutputPort();

    @Autowired
    TestEntityManager testEntityManager;

    FornecedorDomain fornecedorDomain;

    @BeforeEach
    void setUp() {
        fornecedorDomain = FornecedorDomainFactory.createFornecedorDomain();
    }

    @Test
    void save() {
        getFornecedorOutputPort().save(fornecedorDomain);

        Fornecedor fornecedorSalvo = testEntityManager.find(Fornecedor.class, fornecedorDomain.id());

        assertNotNull(fornecedorSalvo);
        assertEquals("Goku", fornecedorSalvo.getNome());
        assertEquals("goku@gmail.com", fornecedorSalvo.getEmail());
    }

    @Test
    void findByCnpj() {
        testEntityManager.persistAndFlush(Fornecedor.fromDomain(fornecedorDomain));
        testEntityManager.clear();

        FornecedorDomain fornecedorRecuperado = getFornecedorOutputPort().findFornecedorByCnpj(fornecedorDomain.cnpj());

        assertEquals("50.095.037/0001-19", fornecedorRecuperado.cnpj());
        assertNotNull(fornecedorRecuperado);
    }

    @Test
    void findById() {
        testEntityManager.persistAndFlush(Fornecedor.fromDomain(fornecedorDomain));
        testEntityManager.clear();

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
                .forEach(fornecedorDomain -> {
                            testEntityManager.persistAndFlush(Fornecedor.fromDomain(fornecedorDomain));
                            testEntityManager.clear();
                        });

        List<FornecedorDomain> fornecedoreRecupreados = getFornecedorOutputPort().findAll();

        assertEquals(fornecedores.size(), fornecedoreRecupreados.size());
    }

    @Test
    void update() {
        Fornecedor fornecedor = Fornecedor.fromDomain(fornecedorDomain);

        testEntityManager.persistAndFlush(fornecedor); // -> força a sincronizar o estado atual com as entidades do banco
        testEntityManager.clear(); // -> limpa o cache de primeiro nível

        FornecedorDomain updateFornecedor = new FornecedorDomain(
                    fornecedor.getId(),
                "Novo nome",
                "novo@gmail.com",
                "(99)999999998",
                "50.095.037/0001-19",
                "razao 2",
                EnderecoDomainFactory.createEnderecoDomain()
                );

        getFornecedorOutputPort().update(fornecedor.getId(), updateFornecedor);
        testEntityManager.flush();
        testEntityManager.clear();

        Fornecedor fornecedorAtualizado = testEntityManager.find(Fornecedor.class, fornecedor.getId());

        assertNotNull(fornecedorAtualizado);

        assertEquals("Novo nome", fornecedorAtualizado.getNome());
        assertEquals("novo@gmail.com", fornecedorAtualizado.getEmail());
    }

    @Test
    void delete() {
        Fornecedor fornecedor = Fornecedor.fromDomain(fornecedorDomain);

        testEntityManager.persistAndFlush(fornecedor);
        testEntityManager.clear();

        getFornecedorOutputPort().delete(fornecedorDomain);

        Fornecedor fornecedorRemovido = testEntityManager.find(Fornecedor.class, fornecedor.getId());

        assertNull(fornecedorRemovido);
    }
}
