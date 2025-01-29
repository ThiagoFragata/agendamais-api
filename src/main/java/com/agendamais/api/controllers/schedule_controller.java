package com.agendamais.api.controllers;

import com.agendamais.api.config.success_response_config;
import com.agendamais.api.dtos.schedule.schedule_create_record_dto;
import com.agendamais.api.dtos.schedule.schedule_create_response_record_dto;
import com.agendamais.api.dtos.schedule.schedule_update_record_dto;
import com.agendamais.api.models.schedule_model;
import com.agendamais.api.services.schedule_service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("schedules")
public class schedule_controller {
    @Autowired
    private schedule_service schedule_service;

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

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<schedule_create_response_record_dto>> getSchedulesByEmployeeId(@PathVariable Long employeeId) {
        List<schedule_model> schedules = schedule_service.get_schedules_by_employee_id(employeeId);

        List<schedule_create_response_record_dto> response = schedules.stream()
                .map(schedule_create_response_record_dto::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<success_response_config> deleteSchedulesByEmployeeId(@PathVariable Long employeeId) {
        schedule_service.delete_schedules_by_employee_id(employeeId);

        return ResponseEntity.ok(new success_response_config("Horários deletados com sucesso."));

    }

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
