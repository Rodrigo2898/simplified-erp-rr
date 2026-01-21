package com.ms.rr.pessoa_service.adapter.input.handler;

import com.ms.rr.pessoa_service.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FornecedorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleFornecedorNotFoundException(
            FornecedorNotFoundException e, WebRequest request) {

        ApiError error = ApiError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .description(BaseErrorMessage.FORNECEDOR_NOT_FOUND.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleClienteNotFoundException(
            ClienteNotFoundException e, WebRequest request) {

        ApiError error = ApiError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .description(BaseErrorMessage.CLIENTE_NOT_FOUND.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmailExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> handleEmailExistsException(
            EmailExistsException e, WebRequest request) {

        ApiError error = ApiError.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(e.getMessage())
                .description(BaseErrorMessage.EXISTS_PESSOA_EMAIL.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CNPJExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> handleCNPJExistsException(
            CNPJExistsException e, WebRequest request) {

        ApiError error = ApiError.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(e.getMessage())
                .description(BaseErrorMessage.EXISTS_PESSOA_FORNECEDOR_CNPJ.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CPFExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> handleCPFExistsException(
            CPFExistsException e, WebRequest request) {

        ApiError error = ApiError.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(e.getMessage())
                .description(BaseErrorMessage.EXISTS_PESSOA_CLIENTE_CPF.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleException(final Exception exception,
                                                    final WebRequest request) {

        var error = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.build());
    }
}
