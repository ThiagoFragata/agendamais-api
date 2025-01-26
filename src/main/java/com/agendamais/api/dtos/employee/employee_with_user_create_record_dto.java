package com.agendamais.api.dtos.employee;

import com.agendamais.api.dtos.user.user_record_dto;

public record employee_with_user_create_record_dto(
        String cpf,
        String photo,
        Long storeId,
        user_record_dto employee
) {}
