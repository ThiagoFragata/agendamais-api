package com.agendamais.api.dtos.auth;

import jakarta.validation.constraints.*;

public record auth_record_dto(
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser válido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        String password
) {}
