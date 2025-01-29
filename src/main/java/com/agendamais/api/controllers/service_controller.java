package com.agendamais.api.controllers;

import com.agendamais.api.dtos.service.service_create_update_record_dto;
import com.agendamais.api.dtos.service.service_response_record_dto;
import com.agendamais.api.models.service_model;
import com.agendamais.api.services.service_service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("services")
public class service_controller {

    @Autowired
    private service_service service_service;


    @PostMapping
    public ResponseEntity<service_response_record_dto> create_service(@RequestBody @Valid service_create_update_record_dto service_dto) {
        service_model service = service_service.create_service(service_dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new service_response_record_dto(service));
    }

    @GetMapping
    public ResponseEntity<List<service_response_record_dto>> get_all_services() {
        List<service_model> services = service_service.get_all_services();
        List<service_response_record_dto> response = services.stream()
                .map(service_response_record_dto::new)
                .toList();
        return services.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<service_response_record_dto>> get_services_by_store(@PathVariable Long storeId) {
        List<service_model> services = service_service.get_services_by_store(storeId);
        List<service_response_record_dto> response = services.stream()
                .map(service_response_record_dto::new)
                .toList();
        return services.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<service_response_record_dto> get_service_by_id(@PathVariable Long id) {
        service_model service = service_service.get_service_by_id(id);
        return ResponseEntity.ok(new service_response_record_dto(service));
    }

    @PutMapping("/{id}")
    public ResponseEntity<service_response_record_dto> update_service(
            @PathVariable Long id,
            @RequestBody @Valid service_create_update_record_dto service_dto) {
        service_model updatedService = service_service.update_service(id, service_dto);
        return ResponseEntity.ok(new service_response_record_dto(updatedService));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate_service(@PathVariable Long id) {
        service_service.deactivate_service(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activate_service(@PathVariable Long id) {
        service_service.activate_service(id);
        return ResponseEntity.noContent().build();
    }

}
