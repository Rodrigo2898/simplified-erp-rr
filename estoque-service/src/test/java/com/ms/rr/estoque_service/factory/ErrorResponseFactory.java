package com.ms.rr.estoque_service.factory;

import com.ms.rr.estoque_service.adapter.input.handler.ApiError;

import java.time.LocalDateTime;

public class ErrorResponseFactory {

    public static ApiError buildApiError(int status,
                                         String description,
                                         LocalDateTime timestamp) {
        return ApiError.builder()
                .status(status)
                .description(description)
                .timestamp(timestamp)
                .build();
    }
}
