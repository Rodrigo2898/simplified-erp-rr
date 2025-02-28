package com.ms.rr.pessoa_service.infrastructure.adapter.output.persistence.entity.enums;

import com.ms.rr.pessoa_service.domain.model.TipoPessoaDomain;

public enum TipoPessoa {
    CLIENTE,
    FORNECEDOR;

    public TipoPessoaDomain toDomain() {
        return TipoPessoaDomain.valueOf(name());
    }

    public static TipoPessoa fromDomain(TipoPessoaDomain domain) {
        return TipoPessoa.valueOf(domain.name());
    }
}
