package com.agendamais.api.dtos.user;

import com.agendamais.api.models.user_model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record user_response_record_dto(
        @NotNull Long id,
        @NotBlank @Size(max = 255) String email,
        @NotBlank @Size(max = 255) String name,
        @NotBlank @Size(max = 15) String phone
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