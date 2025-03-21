package com.agendamais.api.controllers;

import com.agendamais.api.dtos.address.address_record_dto;
import com.agendamais.api.dtos.address.address_response_record_dto;
import com.agendamais.api.models.address_model;
import com.agendamais.api.services.address_service;
import com.agendamais.api.config.error_response_config;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Tag(name = "Endereços", description = "Endpoints de endereço")
@RestController
@RequestMapping("address")
public class address_controller {

    @Autowired
    private address_service address_service;

    @Operation(summary = "Criar endereço")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'EMPLOYEE', 'CUSTOMER')")
    @PostMapping
    public ResponseEntity<Object> create_address(@RequestBody @Valid address_record_dto address_create_record_dto, BindingResult binding_result) {
        if (binding_result.hasErrors()) {
            return create_error_response(HttpStatus.BAD_REQUEST, get_error_messages(binding_result));
        }

        try {
            address_model created_address = address_service.create_address(address_create_record_dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created_address);
        } catch (IllegalArgumentException e) {
            return create_error_response(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return create_error_response(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao criar o endereço.");
        }
    }

    @Operation(summary = "Buscar todos os endereços")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Object> find_all_address() {
        List<address_model> addresses = address_service.find_all_address();

        if (addresses.isEmpty()) {
            return create_error_response(HttpStatus.NO_CONTENT, "Lista de endereços vazia.");
        }

        List<address_response_record_dto> addressDtos = addresses.stream()
                .map(address_response_record_dto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(addressDtos);
    }

    @Operation(summary = "Buscar endereço pelo ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'EMPLOYEE', 'CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> find_address_by_id(@PathVariable Long id) {
        return address_service.find_address_by_id(id)
                .map(address -> ResponseEntity.ok((Object) new address_response_record_dto(address))) // Mapeando para DTO
                .orElseGet(() -> create_error_response(HttpStatus.NOT_FOUND, "Endereço não encontrado."));
    }

    @Operation(summary = "Deletar enderço")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'EMPLOYEE', 'CUSTOMER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete_address(@PathVariable Long id) {
        try {

            address_service.delete_address(id);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Endereço deletado com sucesso");

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return create_error_response(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return create_error_response(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao deletar o endereço.");
        }
    }

    private List<String> get_error_messages(BindingResult binding_result) {
        List<String> error_messages = new ArrayList<>();
        binding_result.getAllErrors().forEach(error -> error_messages.add(error.getDefaultMessage()));
        return error_messages;
    }

    private ResponseEntity<Object> create_error_response(HttpStatus status, Object message) {
        error_response_config errorResponse = new error_response_config(status.value(), message);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
