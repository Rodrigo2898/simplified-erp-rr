package com.ms.rr.produto_service.infrastructure.adapter.input.web.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.rr.produto_service.domain.exception.ProdutoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ProductNotFoundHandler extends AbstractHandleException<ProdutoNotFoundException> {

    protected ProductNotFoundHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handleException(ServerWebExchange exchange, ProdutoNotFoundException ex) {
        return Mono.fromCallable(() -> {
            preparedExchange(exchange, HttpStatus.NOT_FOUND);
            return ex.getMessage();
        }).map(message -> buildError(HttpStatus.NOT_FOUND, message))
                .doFirst(() -> log.error("==== ProdutoNotFoundException", ex))
                .flatMap(apiError -> writeResponse(exchange, apiError));
    }
}
