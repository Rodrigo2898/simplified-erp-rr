package com.ms.rr.estoque_service.factory;

import com.ms.rr.estoque_service.domain.model.ProdutoCriadoEvent;

import java.util.UUID;

public class ProdutoCriadoEventFactory {

    public static ProdutoCriadoEvent buildProdutoCriadoEvent() {
        return new ProdutoCriadoEvent(
                UUID.randomUUID(),
                123456L,
                "Camisa Chelsea",
                "Roupas"
        );
    }
}
