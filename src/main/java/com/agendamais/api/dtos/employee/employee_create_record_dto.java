package com.agendamais.api.dtos.employee;

import jakarta.validation.constraints.NotBlank;

public record employee_create_record_dto(
        @NotBlank(message = "O CPF é obrigatório")
        String cpf,
        String photo,

        Long user_id,
        Long store_id
) {}
