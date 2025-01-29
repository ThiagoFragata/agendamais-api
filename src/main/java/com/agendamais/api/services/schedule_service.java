package com.agendamais.api.services;

import com.agendamais.api.models.employee_model;
import com.agendamais.api.models.schedule_model;
import com.agendamais.api.repositories.employee_repository;
import com.agendamais.api.repositories.schedule_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class schedule_service {

    @Autowired
    private schedule_repository schedule_repository;

    @Autowired
    private employee_repository employee_repository;

    public List<schedule_model> create_schedules(String startTime, String endTime, int duration, Long employeeId) {
        employee_model employee = employee_repository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start = LocalTime.parse(startTime, formatter);
        LocalTime end = LocalTime.parse(endTime, formatter);

        List<schedule_model> schedules = new ArrayList<>();

        while (start.isBefore(end)) {
            schedule_model schedule = new schedule_model();
            schedule.setSchedule(start.format(formatter));
            schedule.setAvailable(true);
            schedule.setEmployee(employee);

            schedules.add(schedule);
            start = start.plusMinutes(duration);
        }

        return schedule_repository.saveAll(schedules);
    }

    public List<schedule_model> get_schedules_by_employee_id(Long employee_id) {
        return schedule_repository.findByEmployeeIdOrderByIdAsc(employee_id);
    }

    @Transactional
    public void delete_schedules_by_employee_id(Long employeeId) {
        schedule_repository.deleteByEmployeeId(employeeId);
    }

    public schedule_model update_schedule_availability(Long scheduleId, boolean isAvailable) {
        schedule_model schedule = schedule_repository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Horário não encontrado"));

        schedule.setAvailable(isAvailable);
        return schedule_repository.save(schedule);
    }
}
