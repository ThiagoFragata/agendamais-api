package com.agendamais.api.config;

public class success_response {
    private String message;

    public success_response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}