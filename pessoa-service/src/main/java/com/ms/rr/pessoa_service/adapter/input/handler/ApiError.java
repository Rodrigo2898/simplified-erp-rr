package com.ms.rr.pessoa_service.adapter.input.handler;

import java.time.LocalDateTime;

public record ApiError(int status,
                       String message,
                       String description,
                       LocalDateTime timestamp) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int status;
        private String message;
        private String description;
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

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ApiError build() {
            return new ApiError(status, message, description,timestamp);
        }
    }
}
