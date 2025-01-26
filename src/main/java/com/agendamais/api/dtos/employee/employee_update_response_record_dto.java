package com.agendamais.api.dtos.employee;

import com.agendamais.api.dtos.user.user_response_record_dto;
import com.agendamais.api.models.employee_model;

public record employee_update_response_record_dto(
        Long id,
        Long store_id,
        String cpf,
        String photo,
        boolean active,
        user_response_record_dto user
) {
    public employee_update_response_record_dto(employee_model employee) {
        this(
                employee.getId(),
                employee.getStore().getId(),
                employee.getCpf(),
                employee.getPhoto(),
                employee.getActive(),
                new user_response_record_dto(employee.getUser())
        );
    }
}