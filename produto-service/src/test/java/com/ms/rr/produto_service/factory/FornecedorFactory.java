package com.ms.rr.produto_service.factory;

import com.ms.rr.produto_service.domain.model.FornecedorDomain;

public class FornecedorFactory {

    public static FornecedorDomain createFornecedor() {
        return new FornecedorDomain(
                123456789L,
                "FornecedorA",
                "FornecedorA@email.com",
                "99999999",
                "1111111111111",
                "FornecedorA (LTDA)");
    }
}
