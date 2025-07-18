package com.ms.rr.produto_service.adapter.input.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.rr.produto_service.domain.exception.FornecedorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class FornecedorNotFoundHandler extends AbstractHandleException<FornecedorNotFoundException> {

    protected FornecedorNotFoundHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handleException(ServerWebExchange exchange, FornecedorNotFoundException ex) {
        return Mono.fromCallable(() -> {
            preparedExchange(exchange, HttpStatus.NOT_FOUND);
            return ex.getMessage();
        }).map(message -> buildError(HttpStatus.NOT_FOUND, message))
                .doFirst(() -> log.error("==== FornecedorNotFoundException", ex))
                .flatMap(apiError -> writeResponse(exchange, apiError));
    }
}
