package com.ms.rr.estoque_service.domain.exception;

public class ProdutoNotFoundException extends EstoqueDomainException {

    public ProdutoNotFoundException(String message) {
        super(message);
    }
}
