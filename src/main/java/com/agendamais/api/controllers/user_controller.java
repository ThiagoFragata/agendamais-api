package com.agendamais.api.controllers;

import com.agendamais.api.dtos.user.user_record_dto;
import com.agendamais.api.dtos.user.user_response_record_dto;
import com.agendamais.api.models.user_model;
import com.agendamais.api.services.user_service;
import com.agendamais.api.config.error_response_config;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("users")
public class user_controller {

    @Autowired
    private user_service user_service;

    @PostMapping
    public ResponseEntity<Object> create_user(@RequestBody @Valid user_record_dto user, BindingResult binding_result) {
        if (binding_result.hasErrors()) {
            List<String> error_messages = new ArrayList<>();
            binding_result.getAllErrors().forEach(error -> error_messages.add(error.getDefaultMessage()));
            return create_error_response(HttpStatus.BAD_REQUEST, error_messages);
        }

        user_model new_user = user_service.create_user(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new_user);
    }

    @GetMapping
    public ResponseEntity<Object> get_all_users() {
        List<user_response_record_dto> users = user_service.find_all_users();

        if (users.isEmpty()) {
            return create_error_response(HttpStatus.NO_CONTENT, "Lista de usuários vazia.");
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get_user_by_id(@PathVariable Long id) {
        Optional<user_response_record_dto> user_dto = user_service.find_user_by_id(id);

        return user_dto.map(user -> ResponseEntity.ok((Object) user))
                .orElseGet(() -> create_error_response(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete_user(@PathVariable Long id) {
        user_service.delete_user(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário deletado com sucesso");

        return ResponseEntity.ok(response);
    }

    private ResponseEntity<Object> create_error_response(HttpStatus status, Object message) {
        error_response_config errorResponse = new error_response_config(status.value(), message);
        return ResponseEntity.status(status).body(errorResponse);
    }
}