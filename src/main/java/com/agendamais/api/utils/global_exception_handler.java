package com.agendamais.api.utils;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class global_exception_handler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<error_response> handleRuntimeException(RuntimeException ex) {
        error_response error = new error_response(500, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<error_response> handleIllegalArgumentException(IllegalArgumentException ex) {
        error_response error = new error_response(400, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<error_response> handleEntityNotFoundException(EntityNotFoundException ex) {
        error_response error = new error_response(404, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<error_response> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = "A operação falhou devido a dados conflitantes no banco de dados.";

        if (ex.getCause() != null && ex.getCause().getMessage() != null && ex.getCause().getMessage().contains("duplicate key value")) {
            message = "A chave fornecida já existe no banco de dados. Verifique se os dados estão corretos.";
        }

        error_response error = new error_response(409, message);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
