package com.ms.rr.produto_service.infrastructure.adapter.input.web.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GenericHanlder extends AbstractHandleException<Exception> {

    protected GenericHanlder(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handleException(ServerWebExchange exchange, Exception ex) {
        return Mono.fromCallable(() -> {
            preparedExchange(exchange, HttpStatus.INTERNAL_SERVER_ERROR);
            return ex.getMessage();
        }).map(message -> buildError(HttpStatus.INTERNAL_SERVER_ERROR, message))
                .doFirst(() -> log.error("==== Exception", ex))
                .flatMap(apiError -> writeResponse(exchange, apiError));
    }
}
