package com.ms.rr.pessoa_service.domain.exception;

public class CNPJExistsException extends PessoaDomainException {
    public CNPJExistsException(String message) {
        super(message);
    }
}
