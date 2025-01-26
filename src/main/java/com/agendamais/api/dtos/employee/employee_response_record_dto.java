package com.agendamais.api.dtos.employee;

import com.agendamais.api.models.employee_model;

public record employee_response_record_dto(
        Long id,
        Long store_id,
        Long user_id,
        String cpf,
        String name,
        String email,
        String phone,
        String photo
) {
    public employee_response_record_dto(employee_model employee) {
        this(
                employee.getId(),
                employee.getStore().getId(),
                employee.getUser().getId(),
                employee.getCpf(),
                employee.getUser().getName(),
                employee.getUser().getEmail(),
                employee.getUser().getPhone(),
                employee.getPhoto()
        );
    }
}
