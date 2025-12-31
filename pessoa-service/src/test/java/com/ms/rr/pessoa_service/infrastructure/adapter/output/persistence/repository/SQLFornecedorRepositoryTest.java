package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.repository;

import com.ms.rr.pessoa_service.adapter.output.persistence.repository.impl.SQLFornecedorRepository;
import com.ms.rr.pessoa_service.api.output.FornecedorOutputPortTest;
import com.ms.rr.pessoa_service.domain.port.output.FornecedorOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;


@Import(SQLFornecedorRepository.class)
public class SQLFornecedorRepositoryTest extends FornecedorOutputPortTest {

    @Autowired
    FornecedorOutputPort fornecedorOutputPort;

    @Override
    public FornecedorOutputPort getFornecedorOutputPort() {
        return fornecedorOutputPort;
    }
}
