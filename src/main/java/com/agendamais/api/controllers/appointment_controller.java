package com.agendamais.api.controllers;

import com.agendamais.api.config.success_response_config;
import com.agendamais.api.dtos.appointment.appointment_create_record_dto;
import com.agendamais.api.dtos.appointment.appointment_response_record_dto;
import com.agendamais.api.dtos.appointment.update_appointment_status_record_dto;
import com.agendamais.api.models.appointment_model;
import com.agendamais.api.services.appointment_service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Agendamentos", description = "Endpoints de agendamentos")
@RestController
@RequestMapping("appointments")
public class appointment_controller {

    @Autowired
    private appointment_service appointment_service;

    @Operation(summary = "Criar agendamento")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'CUSTOMER')")
    @PostMapping
    public ResponseEntity<success_response_config> create_appointment(
            @Valid @RequestBody appointment_create_record_dto appointment_dto) {
        appointment_model appointment = appointment_service.create_appointment(appointment_dto);

        return ResponseEntity.ok(new success_response_config("Serviço agendado com sucesso."));
    }

    @Operation(summary = "Buscar todos os agendamentos")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<appointment_response_record_dto>> get_all_appointments() {
        List<appointment_model> appointments = appointment_service.get_all_appointments();
        List<appointment_response_record_dto> response = appointments.stream()
                .map(appointment_response_record_dto::new)
                .toList();
        return appointments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar agendamentos da loja")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<appointment_response_record_dto>> get_appointments_by_store(@PathVariable Long storeId) {
        List<appointment_model> appointments = appointment_service.get_appointments_by_store(storeId);
        List<appointment_response_record_dto> response = appointments.stream()
                .map(appointment_response_record_dto::new)
                .toList();
        return appointments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar agendamentos do funcionário")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'EMPLOYEE')")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<appointment_response_record_dto>> get_appointments_by_employee(@PathVariable Long employeeId) {
        List<appointment_model> appointments = appointment_service.get_appointments_by_employee(employeeId);
        List<appointment_response_record_dto> response = appointments.stream()
                .map(appointment_response_record_dto::new)
                .toList();
        return appointments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar agendamento")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'EMPLOYEE', 'CUSTOMER')")
    @PutMapping("/{appointmentId}/status")
    public ResponseEntity<appointment_response_record_dto> update_appointment_status(
            @PathVariable Long appointmentId,
            @RequestBody @Valid update_appointment_status_record_dto status_dto) {

        appointment_model updatedAppointment = appointment_service.update_appointment_status(appointmentId, status_dto);

        return ResponseEntity.ok(new appointment_response_record_dto(updatedAppointment));
    }
}
