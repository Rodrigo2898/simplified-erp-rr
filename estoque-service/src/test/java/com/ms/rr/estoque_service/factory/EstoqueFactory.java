package com.ms.rr.estoque_service.factory;

import com.ms.rr.estoque_service.domain.dto.in.CreateEstoque;
import com.ms.rr.estoque_service.domain.dto.out.EstoqueResponse;
import com.ms.rr.estoque_service.domain.model.EstoqueDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    public static Page<EstoqueDomain> createWithPage() {
        return new PageImpl<>(List.of(estoqueDomain()));
    }

    public static EstoqueResponse createEstoqueResponse(EstoqueDomain estoqueDomain) {
        return EstoqueResponse.fromDomain(estoqueDomain);
    }

    public static Page<EstoqueResponse> createWithPageEstoqueResponse() {
        var response1 = createEstoqueResponse(estoqueDomain());
        var response2 = createEstoqueResponse(estoqueDomain());
        var response3 = createEstoqueResponse(estoqueDomain());
        return new PageImpl<>(List.of(response1, response2, response3));
    }
}
