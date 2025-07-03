package com.ms.rr.produto_service.infrastructure.adapter.input.web.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public abstract class AbstractHandleException<T extends Exception> {

    private final ObjectMapper objectMapper;
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected AbstractHandleException(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public abstract Mono<Void> handleException(final ServerWebExchange exchange, final T ex);

    protected void preparedExchange(final ServerWebExchange exchange, final HttpStatus statusCode) {
        exchange.getResponse().setStatusCode(statusCode);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
    }

    protected ApiError buildError(final HttpStatus status, final String errorDescription) {
        return new ApiError(status.value(), errorDescription, LocalDateTime.now());
    }

    protected Mono<Void> writeResponse(ServerWebExchange exchange, ApiError problemResponse) {
        return exchange.getResponse()
                .writeWith(Mono.fromCallable(() -> new DefaultDataBufferFactory()
                        .wrap(objectMapper.writeValueAsBytes(problemResponse))));
    }
}
