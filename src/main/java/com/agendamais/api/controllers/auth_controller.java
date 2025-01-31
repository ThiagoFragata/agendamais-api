package com.agendamais.api.controllers;

import com.agendamais.api.config.error_response_config;
import com.agendamais.api.dtos.auth.auth_record_dto;
import com.agendamais.api.dtos.auth.auth_response_record_dto;
import com.agendamais.api.dtos.user.user_record_dto;
import com.agendamais.api.models.user_model;
import com.agendamais.api.services.auth_service;
import com.agendamais.api.services.user_service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Tag(name = "Autenticação", description = "Endpoints de autenticação")
@RestController
@RequestMapping("/auth")
public class auth_controller {

    @Autowired
    private auth_service authService;

    @Autowired
    private user_service user_service;

    @Operation(
            summary = "Autenticar usuário",
            description = "Recebe e-mail e senha e retorna um token JWT",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Autenticado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = auth_response_record_dto.class))),
                    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody auth_record_dto request) {
        String token = authService.authenticate(request.email(), request.password());
        Map<String, String> response = Map.of("access_token", token);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Registrar  usuário",
            description = "Recebe campos de nome, telefone, e-mail e senha"
    )
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid user_record_dto user, BindingResult binding_result) {
        if (binding_result.hasErrors()) {
            List<String> error_messages = new ArrayList<>();
            binding_result.getAllErrors().forEach(error -> error_messages.add(error.getDefaultMessage()));
            return create_error_response (error_messages);
        }

        user_model new_user = user_service.create_user(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new_user);
    }

    private ResponseEntity<Object> create_error_response(Object message) {
        error_response_config errorResponse = new error_response_config(HttpStatus.BAD_REQUEST.value(), message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
