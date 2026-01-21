package com.ms.rr.pessoa_service.domain.exception;

public class CPFExistsException extends PessoaDomainException {
    public CPFExistsException(String message) {
        super(message);
    }
}
