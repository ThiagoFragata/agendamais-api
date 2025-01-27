package com.agendamais.api.dtos.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record employee_create_with_existing_user_record_dto(
        @NotNull(message = "O ID do usuário não pode ser nulo")
        Long user_id,

        @NotBlank(message = "O CPF não pode ser vazio")
        @Size(max = 14, message = "O CPF deve ter no máximo 14 caracteres")
        String cpf,

        @NotNull(message = "O status de ativação não pode ser nulo")
        Boolean active,

        @NotBlank(message = "A foto é obrigatória")
        String photo,

        @NotNull(message = "O ID da loja não pode ser nulo")
        Long store_id
) {}

