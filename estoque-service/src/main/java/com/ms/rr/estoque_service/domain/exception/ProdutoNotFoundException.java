package com.ms.rr.estoque_service.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProdutoNotFoundException extends EstoqueDomainException {

    public ProdutoNotFoundException(String message) {
        super(message);
    }
}
