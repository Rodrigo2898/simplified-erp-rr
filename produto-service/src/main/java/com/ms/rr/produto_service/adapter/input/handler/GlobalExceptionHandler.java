package com.ms.rr.produto_service.adapter.input.handler;

import com.ms.rr.produto_service.domain.exception.FornecedorNotFoundException;
import com.ms.rr.produto_service.domain.exception.InvalidPaginationException;
import com.ms.rr.produto_service.domain.exception.ProdutoNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalExceptionHandler implements WebExceptionHandler {

    private final ProductNotFoundHandler productNotFoundHandler;
    private final FornecedorNotFoundHandler fornecedorNotFoundHandler;
    private final InvalidPaginationHandler invalidPaginationHandler;
    private final GenericHanlder genericHanlder;

    public GlobalExceptionHandler(ProductNotFoundHandler productNotFoundHandler,
                                  FornecedorNotFoundHandler fornecedorNotFoundHandler,
                                  InvalidPaginationHandler invalidPaginationHandler,
                                  GenericHanlder genericHanlder) {
        this.productNotFoundHandler = productNotFoundHandler;
        this.fornecedorNotFoundHandler = fornecedorNotFoundHandler;
        this.invalidPaginationHandler = invalidPaginationHandler;
        this.genericHanlder = genericHanlder;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        return Mono.error(ex)
                .onErrorResume(ProdutoNotFoundException.class, e -> productNotFoundHandler.handleException(exchange, e))
                .onErrorResume(FornecedorNotFoundException.class, e -> fornecedorNotFoundHandler.handleException(exchange, e))
                .onErrorResume(InvalidPaginationException.class, e -> invalidPaginationHandler.handleException(exchange, e))
                .onErrorResume(Exception.class, e -> genericHanlder.handleException(exchange, e))
                .then();
    }
}
