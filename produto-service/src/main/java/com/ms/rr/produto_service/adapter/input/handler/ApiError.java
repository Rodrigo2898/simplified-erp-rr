package com.ms.rr.produto_service.adapter.input.handler;

import java.time.LocalDateTime;

public record ApiError(int status,
                       String message,
                       LocalDateTime timestamp) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int status;
        private String message;
        private LocalDateTime timestamp;

        public Builder() {
        }

        public Builder(int status, String message, LocalDateTime timestamp) {
            this.status = status;
            this.message = message;
            this.timestamp = timestamp;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ApiError build() {
            return new ApiError(status, message, timestamp);
        }
    }
}
