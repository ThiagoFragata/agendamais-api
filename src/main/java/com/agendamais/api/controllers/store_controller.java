package com.agendamais.api.controllers;

import com.agendamais.api.dtos.address.address_response_record_dto;
import com.agendamais.api.dtos.store.store_response_record_dto;
import com.agendamais.api.dtos.store.store_with_address_record_dto;
import com.agendamais.api.models.address_model;
import com.agendamais.api.models.store_model;
import com.agendamais.api.services.store_service;
import com.agendamais.api.config.error_response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/stores")
public class store_controller {

    @Autowired
    private store_service store_service;

    @PostMapping
    public ResponseEntity<store_with_address_record_dto> create_store_with_address(@Valid @RequestBody store_with_address_record_dto store) {
        store_with_address_record_dto response = store_service.create_store_with_address(store);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{storeId}/address")
    public ResponseEntity<Map<String, String>> update_store_address(@PathVariable Long storeId, @RequestBody store_with_address_record_dto new_address) {
            store_service.update_store_address(storeId, new_address.address());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Endereço atualizado com sucesso");

            return ResponseEntity.ok(response);
    }

    @GetMapping("/{storeId}/address")
    public ResponseEntity<address_response_record_dto> get_store_address(@PathVariable Long storeId) {
        address_model address = store_service.get_store_address(storeId);

        return ResponseEntity.ok(new address_response_record_dto(address));
    }

    @GetMapping
    public ResponseEntity<List<store_response_record_dto>> get_all_stores() {
        List<store_model> stores = store_service.find_all_stores();

        if (stores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<store_response_record_dto> response = stores.stream()
                .map(store_response_record_dto::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get_store_by_id(@PathVariable Long id) {
        Optional<store_model> store = store_service.find_store_by_id(id);

        if (store.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Loja não encontrada."); // Retorno como String no caso de erro
        }

        return ResponseEntity.ok(new store_response_record_dto(store.get()));
    }

    private ResponseEntity<Object> create_error_response(HttpStatus status, Object message) {
        error_response errorResponse = new error_response(status.value(), message);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
