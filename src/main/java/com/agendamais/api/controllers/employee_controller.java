package com.agendamais.api.controllers;

import com.agendamais.api.dtos.employee.*;
import com.agendamais.api.models.employee_model;
import com.agendamais.api.services.employee_service;
import com.agendamais.api.config.success_response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/employees")
public class employee_controller {

    @Autowired
    private employee_service employee_service;


    @PostMapping("/with-existing-user")
    public ResponseEntity<employee_response_record_dto> create_employee_with_existing_user(
            @RequestBody @Valid employee_create_with_existing_user_record_dto employee_dto) {

        employee_model employee = employee_service.create_employee_with_existing_user(employee_dto);

        employee_response_record_dto responseDto = new employee_response_record_dto(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping
    public ResponseEntity<employee_response_record_dto> create_employee_with_user(
            @RequestBody @Valid employee_create_with_user_record_dto employee_dto) {

        employee_model employee = employee_service.create_employee_with_user(employee_dto);

        employee_response_record_dto responseDto = new employee_response_record_dto(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<employee_list_response_record_dto>> get_all_employees() {
        List<employee_model> employees = employee_service.get_all_employees();
        List<employee_list_response_record_dto> response = employees.stream()
                .map(employee_list_response_record_dto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<employee_list_response_record_dto>> get_employees_by_store(@PathVariable Long storeId) {
        List<employee_model> employees = employee_service.get_employees_by_store(storeId);
        List<employee_list_response_record_dto> response = employees.stream()
                .map(employee_list_response_record_dto::new)
                .collect(Collectors.toList());
        return employees.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<employee_list_response_record_dto> get_employee_by_id(@PathVariable Long id) {
        employee_model employee = employee_service.get_employee_by_id(id);
        employee_list_response_record_dto responseDto = new employee_list_response_record_dto(employee);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<employee_update_response_record_dto> update_employee(
            @PathVariable Long id,
            @RequestBody @Valid employee_update_record_dto employee_dto) {

        employee_model updatedEmployee = employee_service.update_employee(id, employee_dto);

        employee_update_response_record_dto responseDto = new employee_update_response_record_dto(updatedEmployee);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<success_response> deactivate_employee(@PathVariable Long id) {
        employee_service.deactivate_employee(id);
        success_response response = new success_response("Funcionário desativado com sucesso");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<success_response> activate_employee(@PathVariable Long id) {
        employee_service.activate_employee(id);
        success_response response = new success_response("Funcionário ativado com sucesso");
        return ResponseEntity.ok(response);
    }
}
