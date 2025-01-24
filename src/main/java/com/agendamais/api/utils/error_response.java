package com.agendamais.api.utils;

public class error_response {
    private int error;
    private Object message;

    public error_response(int error, Object message) {
        this.error = error;
        this.message = message;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
