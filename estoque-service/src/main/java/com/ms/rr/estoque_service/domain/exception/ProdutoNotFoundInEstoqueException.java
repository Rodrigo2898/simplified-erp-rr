package com.ms.rr.estoque_service.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProdutoNotFoundInEstoqueException extends EstoqueDomainException {
    public ProdutoNotFoundInEstoqueException(String message) {
        super(message);
    }
}
