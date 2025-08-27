package com.ms.rr.estoque_service.domain.exception;

public class ProdutoNotFoundInEstoqueException extends EstoqueDomainException {
    public ProdutoNotFoundInEstoqueException(String message) {
        super(message);
    }
}
