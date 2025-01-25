package com.agendamais.api.controllers;

import com.agendamais.api.dtos.address.address_record_dto;
import com.agendamais.api.models.address_model;
import com.agendamais.api.services.address_service;
import com.agendamais.api.utils.error_response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/address")
public class address_controller {

    @Autowired
    private address_service address_service;

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

    @GetMapping
    public ResponseEntity<Object> find_all_address() {
        List<address_model> addresses = address_service.find_all_address();

        if (addresses.isEmpty()) {
            return create_error_response(HttpStatus.NO_CONTENT, "Lista de endereços vazia.");
        }

        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> find_address_by_id(@PathVariable Long id) {
        return address_service.find_address_by_id(id)
                .map(address -> ResponseEntity.ok((Object) address))
                .orElseGet(() -> create_error_response(HttpStatus.NOT_FOUND, "Endereço não encontrado."));
    }

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
        error_response errorResponse = new error_response(status.value(), message);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
