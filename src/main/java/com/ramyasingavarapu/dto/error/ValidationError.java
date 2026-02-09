package com.ramyasingavarapu.dto.error;

public class ValidationError {
    private String message;
    private String field;

    public ValidationError() {}

    public ValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}