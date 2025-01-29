package com.agendamais.api.config;

public class success_response_config {
    private String message;

    public success_response_config(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}