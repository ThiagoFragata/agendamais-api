package com.agendamais.api.dtos.employee;

public record employee_update_record_dto(
        String photo,
        Boolean active,
        int store_id
) {}
