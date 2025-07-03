package com.ms.rr.produto_service.infrastructure.adapter.input.web.handler;

import java.time.LocalDateTime;

public record ApiError(int status,
                       String message,
                       LocalDateTime timestamp) {
}
