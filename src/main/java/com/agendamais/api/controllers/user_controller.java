package com.agendamais.api.controllers;

import com.agendamais.api.dtos.user.user_create_record_dto;
import com.agendamais.api.dtos.user.user_response_record_dto;
import com.agendamais.api.models.user_model;
import com.agendamais.api.services.user_service;
import com.agendamais.api.utils.error_response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class user_controller {

    @Autowired
    private user_service user_service;

    @PostMapping
    public ResponseEntity<Object> create_user(@RequestBody @Valid user_create_record_dto user, BindingResult binding_result) {
        if (binding_result.hasErrors()) {
            List<String> error_messages = new ArrayList<>();
            binding_result.getAllErrors().forEach(error -> error_messages.add(error.getDefaultMessage()));

            error_response errorResponse = new error_response(HttpStatus.BAD_REQUEST.value(), error_messages);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        try {
            user_model new_user = user_service.create_user(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new_user);
        } catch (IllegalArgumentException e) {
            error_response error_response = new error_response(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error_response);
        } catch (Exception e) {
            error_response error_response = new error_response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro inesperado ao criar o usuário.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error_response);
        }
    }

    @GetMapping
    public Object get_all_users() {
        List<user_response_record_dto> users = user_service.find_all_users();

        if (users.isEmpty()) {
            error_response error_response = new error_response(HttpStatus.NO_CONTENT.value(), "Lista de usuários vazia.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(error_response);
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get_user_by_id(@PathVariable Long id) {
        Optional<user_response_record_dto> user = user_service.find_user_by_id(id);

        return user.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete_user(@PathVariable Long id) {
        try {
            user_service.delete_user(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
        } catch (IllegalArgumentException e) {
            error_response errorResponse = new error_response(HttpStatus.NOT_FOUND.value(), List.of(e.getMessage()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
