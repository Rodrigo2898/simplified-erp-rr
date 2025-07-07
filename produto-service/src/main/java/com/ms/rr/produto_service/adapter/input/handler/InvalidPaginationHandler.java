package com.ms.rr.produto_service.adapter.input.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.rr.produto_service.domain.exception.InvalidPaginationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class InvalidPaginationHandler extends AbstractHandleException<InvalidPaginationException> {

    protected InvalidPaginationHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handleException(ServerWebExchange exchange, InvalidPaginationException ex) {
        return Mono.fromCallable(() -> {
            preparedExchange(exchange, HttpStatus.BAD_REQUEST);
            return ex.getMessage();
        }).map(message -> buildError(HttpStatus.BAD_REQUEST, message))
                .doFirst(() -> log.error("==== InvalidPaginationException", ex))
                .flatMap(apiError -> writeResponse(exchange, apiError));
    }
}
