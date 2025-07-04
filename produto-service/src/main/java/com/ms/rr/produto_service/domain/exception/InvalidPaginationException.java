package com.ms.rr.produto_service.domain.exception;

public class InvalidPaginationException extends ProdutoDomainException {
    public InvalidPaginationException(String message) {
        super(message);
    }
}
