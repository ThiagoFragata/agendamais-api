package com.agendamais.api.dtos.user;

import com.agendamais.api.enums.role_enum;
import jakarta.validation.constraints.*;

public record user_record_dto(
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser válido")
        String email,

        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotNull(message = "O telefone é obrigatório")
        @Pattern(regexp = "^[0-9]+$", message = "O telefone deve conter apenas números")
        String phone,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
        @Pattern(regexp = ".*[A-Z].*", message = "A senha deve conter pelo menos uma letra maiúscula")
        @Pattern(regexp = ".*[a-z].*", message = "A senha deve conter pelo menos uma letra minúscula")
        @Pattern(regexp = ".*\\d.*", message = "A senha deve conter pelo menos um número")
        @Pattern(regexp = ".*[!@#$%^&*(),.?\":{}|<>].*", message = "A senha deve conter pelo menos um caractere especial")
        String password,

        role_enum role
) {}
