package com.springcrudengine.product_api.exceptions;

import java.time.LocalDateTime;

public class ApiSuccessResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;

    public ApiSuccessResponse(int status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    // Getters and Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}