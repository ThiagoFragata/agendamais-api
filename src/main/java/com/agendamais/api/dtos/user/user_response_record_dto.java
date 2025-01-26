package com.agendamais.api.dtos.user;

import com.agendamais.api.models.user_model;

public record user_response_record_dto(
        Long id,
        String name,
        String email,
        String phone
){
    public user_response_record_dto(user_model user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone()
        );
    }
}