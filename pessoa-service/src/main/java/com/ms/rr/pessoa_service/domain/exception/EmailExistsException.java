package com.ms.rr.pessoa_service.domain.exception;

public class EmailExistsException extends PessoaDomainException{
    public EmailExistsException(String message) {
        super(message);
    }
}
