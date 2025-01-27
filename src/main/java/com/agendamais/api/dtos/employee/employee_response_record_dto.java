package com.agendamais.api.dtos.employee;

import com.agendamais.api.models.employee_model;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record employee_response_record_dto(
        @NotNull
        Long id,

        @NotNull
        Long store_id,

        @NotNull
        Long user_id,

        @NotBlank @Size(max = 14)
        String cpf,

        @NotNull
        String name,

        @NotNull
        @Email(message = "Informe um e-mail válido")
        String email,

        @NotNull
        String phone,

        @NotNull
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
