package com.ms.rr.estoque_service.adapter.input.handler;

import com.ms.rr.estoque_service.domain.exception.ProdutoNotFoundException;
import com.ms.rr.estoque_service.domain.exception.ProdutoNotFoundInEstoqueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static com.ms.rr.estoque_service.domain.exception.BaseErrorMessage.PRODUTO_NOT_FOUND;
import static com.ms.rr.estoque_service.domain.exception.BaseErrorMessage.PRODUTO_NOT_FOUND_IN_ESTOQUE;

@ControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(ProdutoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleConsentNotFoundException(
            ProdutoNotFoundException exception, WebRequest request) {

        var error = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .description(PRODUTO_NOT_FOUND.getMessage())
                .timestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.build());
    }

    @ExceptionHandler(ProdutoNotFoundInEstoqueException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleConsentNotFoundException(
            ProdutoNotFoundInEstoqueException exception, WebRequest request) {

        var error = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .description(PRODUTO_NOT_FOUND_IN_ESTOQUE.getMessage())
                .timestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.build());
    }
}
