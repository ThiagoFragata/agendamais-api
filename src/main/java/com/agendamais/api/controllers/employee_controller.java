package com.agendamais.api.controllers;

import com.agendamais.api.dtos.employee.*;
import com.agendamais.api.models.employee_model;
import com.agendamais.api.services.employee_service;
import com.agendamais.api.config.success_response_config;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Funcionários", description = "Endpoints de funcionário")
@RestController
@RequestMapping("employees")
public class employee_controller {

    @Autowired
    private employee_service employee_service;


    @Operation(summary = "Criar um funcionário pelo ID do usuário")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @PostMapping("/with-existing-user")
    public ResponseEntity<employee_response_record_dto> create_employee_with_existing_user(
            @RequestBody @Valid employee_create_with_existing_user_record_dto employee_dto) {

        employee_model employee = employee_service.create_employee_with_existing_user(employee_dto);

        employee_response_record_dto responseDto = new employee_response_record_dto(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "Criar um funcionário")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @PostMapping
    public ResponseEntity<employee_response_record_dto> create_employee_with_user(
            @RequestBody @Valid employee_create_with_user_record_dto employee_dto) {

        employee_model employee = employee_service.create_employee_with_user(employee_dto);

        employee_response_record_dto responseDto = new employee_response_record_dto(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "Buscar todos os funcionários")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<employee_list_response_record_dto>> get_all_employees() {
        List<employee_model> employees = employee_service.get_all_employees();
        List<employee_list_response_record_dto> response = employees.stream()
                .map(employee_list_response_record_dto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar funcionários pelo ID da loja")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'EMPLOYEE', 'CUSTOMER')")
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

    @Operation(summary = "Buscar funcionário pelo ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<employee_list_response_record_dto> get_employee_by_id(@PathVariable Long id) {
        employee_model employee = employee_service.get_employee_by_id(id);
        employee_list_response_record_dto responseDto = new employee_list_response_record_dto(employee);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Atualizar funcionário pelo ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'EMPLOYEE')")
    @PutMapping("/{id}")
    public ResponseEntity<employee_update_response_record_dto> update_employee(
            @PathVariable Long id,
            @RequestBody @Valid employee_update_record_dto employee_dto) {

        employee_model updatedEmployee = employee_service.update_employee(id, employee_dto);

        employee_update_response_record_dto responseDto = new employee_update_response_record_dto(updatedEmployee);

        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Desativar um funcionário")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<success_response_config> deactivate_employee(@PathVariable Long id) {
        employee_service.deactivate_employee(id);
        success_response_config response = new success_response_config("Funcionário desativado com sucesso");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Ativar um funcionário")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @PutMapping("/{id}/activate")
    public ResponseEntity<success_response_config> activate_employee(@PathVariable Long id) {
        employee_service.activate_employee(id);
        success_response_config response = new success_response_config("Funcionário ativado com sucesso");
        return ResponseEntity.ok(response);
    }
}
