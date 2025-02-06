package com.agendamais.api.controllers;

import com.agendamais.api.config.success_response_config;
import com.agendamais.api.dtos.schedule.schedule_create_record_dto;
import com.agendamais.api.dtos.schedule.schedule_create_response_record_dto;
import com.agendamais.api.dtos.schedule.schedule_update_record_dto;
import com.agendamais.api.models.schedule_model;
import com.agendamais.api.services.schedule_service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Horários", description = "Endpoints referente a horários")
@RestController
@RequestMapping("schedules")
public class schedule_controller {
    @Autowired
    private schedule_service schedule_service;

    @Operation(summary = "Criar horários")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PostMapping("/create")
    public ResponseEntity<success_response_config> createSchedules(
            @Valid @RequestBody schedule_create_record_dto request) {
        List<schedule_model> schedules = schedule_service.create_schedules(
                request.start_time(),
                request.end_time(),
                request.duration(),
                request.employee_id()
        );

        return ResponseEntity.ok(new success_response_config("Horários criados com sucesso."));
    }

    @Operation(summary = "Buscar horários do funcionário")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<schedule_create_response_record_dto>> getSchedulesByEmployeeId(@PathVariable Long employeeId) {
        List<schedule_model> schedules = schedule_service.get_schedules_by_employee_id(employeeId);

        List<schedule_create_response_record_dto> response = schedules.stream()
                .map(schedule_create_response_record_dto::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deletar todos os horários pelo ID do funcionário")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<success_response_config> deleteSchedulesByEmployeeId(@PathVariable Long employeeId) {
        schedule_service.delete_schedules_by_employee_id(employeeId);

        return ResponseEntity.ok(new success_response_config("Horários deletados com sucesso."));

    }

    @Operation(summary = "Ativar e/ou Desativar horário")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'CUSTOMER')")
    @PutMapping("/update/{scheduleId}")
    public ResponseEntity<success_response_config> updateScheduleAvailability(
            @PathVariable Long scheduleId,
            @Valid @RequestBody schedule_update_record_dto request) {

        schedule_model updatedSchedule = schedule_service.update_schedule_availability(
                scheduleId,
                request.is_available()
        );

        return ResponseEntity.ok(new success_response_config("Horário atualizado com sucesso."));
    }
}
