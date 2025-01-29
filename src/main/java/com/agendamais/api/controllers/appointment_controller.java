package com.agendamais.api.controllers;

import com.agendamais.api.config.success_response;
import com.agendamais.api.dtos.appointment.appointment_create_record_dto;
import com.agendamais.api.dtos.appointment.appointment_response_record_dto;
import com.agendamais.api.dtos.appointment.update_appointment_status_record_dto;
import com.agendamais.api.models.appointment_model;
import com.agendamais.api.services.appointment_service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("appointments")
public class appointment_controller {

    @Autowired
    private appointment_service appointment_service;

    @PostMapping
    public ResponseEntity<success_response> create_appointment(
            @Valid @RequestBody appointment_create_record_dto appointment_dto) {
        appointment_model appointment = appointment_service.create_appointment(appointment_dto);

        return ResponseEntity.ok(new success_response("Serviço agendado com sucesso."));
    }

    @GetMapping
    public ResponseEntity<List<appointment_response_record_dto>> get_all_appointments() {
        List<appointment_model> appointments = appointment_service.get_all_appointments();
        List<appointment_response_record_dto> response = appointments.stream()
                .map(appointment_response_record_dto::new)
                .toList();
        return appointments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<appointment_response_record_dto>> get_appointments_by_store(@PathVariable Long storeId) {
        List<appointment_model> appointments = appointment_service.get_appointments_by_store(storeId);
        List<appointment_response_record_dto> response = appointments.stream()
                .map(appointment_response_record_dto::new)
                .toList();
        return appointments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<appointment_response_record_dto>> get_appointments_by_employee(@PathVariable Long employeeId) {
        List<appointment_model> appointments = appointment_service.get_appointments_by_employee(employeeId);
        List<appointment_response_record_dto> response = appointments.stream()
                .map(appointment_response_record_dto::new)
                .toList();
        return appointments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @PutMapping("/{appointmentId}/status")
    public ResponseEntity<appointment_response_record_dto> update_appointment_status(
            @PathVariable Long appointmentId,
            @RequestBody @Valid update_appointment_status_record_dto status_dto) {

        appointment_model updatedAppointment = appointment_service.update_appointment_status(appointmentId, status_dto);

        return ResponseEntity.ok(new appointment_response_record_dto(updatedAppointment));
    }
}
