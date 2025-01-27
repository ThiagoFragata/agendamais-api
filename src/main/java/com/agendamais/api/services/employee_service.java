package com.agendamais.api.services;

import com.agendamais.api.dtos.employee.*;
import com.agendamais.api.dtos.user.user_record_dto;
import com.agendamais.api.models.employee_model;
import com.agendamais.api.models.store_model;
import com.agendamais.api.models.user_model;
import com.agendamais.api.repositories.employee_repository;
import com.agendamais.api.repositories.store_repository;
import com.agendamais.api.repositories.user_repository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class employee_service {

    @Autowired
    private employee_repository employee_repository;

    @Autowired
    private user_repository user_repository;

    @Autowired
    private store_repository store_repository;

    @Autowired
    private user_service user_service;

    public employee_model create_employee_with_existing_user(employee_create_with_existing_user_record_dto employee_dto) {

        user_model user = user_repository.findById(employee_dto.user_id())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        store_model store = store_repository.findById(employee_dto.store_id())
                .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));

        employee_model employee = new employee_model();
        employee.setCpf(employee_dto.cpf());
        employee.setActive(employee_dto.active());
        employee.setPhoto(employee_dto.photo());
        employee.setUser(user);
        employee.setStore(store);

        return employee_repository.save(employee);
    }

    public employee_model create_employee_with_user(employee_create_with_user_record_dto employee_dto) {

        store_model store = store_repository.findById(employee_dto.store_id())
                .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));

        user_model user = user_service.create_user(new user_record_dto(
                employee_dto.email(),
                employee_dto.name(),
                employee_dto.phone(),
                employee_dto.password()
        ));

        employee_model employee = new employee_model();
        employee.setCpf(employee_dto.cpf());
        employee.setActive(employee_dto.active());
        employee.setPhoto(employee_dto.photo());
        employee.setUser(user);
        employee.setStore(store);

        return employee_repository.save(employee);
    }

    public List<employee_model> get_all_employees() {
        return employee_repository.findAll();
    }

    public List<employee_model> get_employees_by_store(Long storeId) {
        return employee_repository.findByStoreId(storeId);
    }

    public employee_model get_employee_by_id(Long id) {
        return employee_repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
    }

    public employee_model update_employee(Long id, employee_update_record_dto employee_dto) {
        employee_model employee = employee_repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));

        store_model store = store_repository.findById((long) employee_dto.store_id())
                .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));

        employee.setPhoto(employee_dto.photo());
        employee.setStore(store);

        return employee_repository.save(employee);
    }

    public void deactivate_employee(Long id) {
        employee_model employee = employee_repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));

        employee.setActive(false);

        employee_repository.save(employee);
    }

    public void activate_employee(Long id) {
        employee_model employee = employee_repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));

        employee.setActive(true);
        employee_repository.save(employee);
    }
}
