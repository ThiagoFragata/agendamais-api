package com.agendamais.api.services;

import com.agendamais.api.enums.appointment_status_enum;
import com.agendamais.api.dtos.appointment.appointment_create_record_dto;
import com.agendamais.api.dtos.appointment.update_appointment_status_record_dto;
import com.agendamais.api.models.*;
import com.agendamais.api.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class appointment_service {

    @Autowired
    private appointment_repository appointment_repository;

    @Autowired
    private schedule_repository schedule_repository;

    @Autowired
    private user_repository user_repository;

    @Autowired
    private employee_repository employee_repository;

    @Autowired
    private service_repository service_repository;

    @Autowired
    private store_repository store_repository;

    @Autowired
    private schedule_service schedule_service;

    @Transactional
    public appointment_model create_appointment(appointment_create_record_dto appointment_dto) {
        schedule_model schedule = schedule_repository.findById(appointment_dto.schedule_id())
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        if (!schedule.getAvailable()) {
            throw new IllegalArgumentException("O horário selecionado não está disponível");
        }

        user_model user = user_repository.findById(appointment_dto.user_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        employee_model employee = employee_repository.findById(appointment_dto.employee_id())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        service_model service = service_repository.findById(appointment_dto.service_id())
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));

        store_model store = store_repository.findById(appointment_dto.store_id())
                .orElseThrow(() -> new EntityNotFoundException("Store not found"));

        if (!employee.getStore().getId().equals(store.getId())) {
            throw new IllegalArgumentException("Funcionário não pertence à loja selecionada");
        }

        if (!schedule.getEmployee().getId().equals(employee.getId())) {
            throw new IllegalArgumentException("Horário não pertence ao funcionário selecionado");
        }

        if (!service.getEmployees().contains(employee)) {
            throw new IllegalArgumentException("O funcionário não está habilitado para esse serviço");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime schedule_time = LocalTime.parse(schedule.getSchedule(), formatter);

        int service_duration = service.getTime();

        LocalTime end_time = schedule_time.plusMinutes(service_duration);

        if (end_time.getHour() >= 22) {
            throw new IllegalArgumentException("O tempo do serviço ultrapassa o horário permitido de expediente");
        }

        schedule_service.update_schedule_availability(schedule.getId(), false);

        appointment_model appointment = new appointment_model();
        appointment.setSchedule(schedule);
        appointment.setUser(user);
        appointment.setEmployee(employee);
        appointment.setService(service);
        appointment.setStore(store);
        appointment.setStatus(appointment_status_enum.valueOf("PENDING"));

        return appointment_repository.save(appointment);
    }

    public List<appointment_model> get_all_appointments() {
        return appointment_repository.findAll();
    }

    public List<appointment_model> get_appointments_by_store(Long store_id) {
        return appointment_repository.findByStoreId(store_id);
    }

    public List<appointment_model> get_appointments_by_employee(Long employee_id) {
        return appointment_repository.findByEmployeeId(employee_id);
    }

    @Transactional
    public appointment_model update_appointment_status(Long appointment_id, update_appointment_status_record_dto status_dto) {
        appointment_model appointment = appointment_repository.findById(appointment_id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        try {
            appointment.setStatus(appointment_status_enum.valueOf(status_dto.status().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido");
        }

        return appointment_repository.save(appointment);
    }
}
