package com.ms.rr.estoque_service.factory;

import com.ms.rr.estoque_service.domain.dto.in.CreateEstoque;
import com.ms.rr.estoque_service.domain.model.EstoqueDomain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class EstoqueFactory {

    public static EstoqueDomain estoqueDomain() {
        return new EstoqueDomain(
                UUID.randomUUID().toString(),
                "Camisa Chelsea",
                "SKU42",
                2,
                "Roupa",
                dataAtualizacao()
        );
    }

    public static CreateEstoque createEstoque() {
        return new CreateEstoque(
                "Camisa Chelsea",
                "SKU42",
                2,
                "Roupa"
        );
    }

    private static String dataAtualizacao() {
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.now().format(customFormatter);
    }
}
