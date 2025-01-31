package com.agendamais.api.dtos.employee;

import com.agendamais.api.enums.role_enum;
import jakarta.validation.constraints.*;

public record employee_create_with_user_record_dto(
        @NotBlank(message = "O CPF não pode ser vazio")
        @Size(max = 14, message = "O CPF deve ter no máximo 14 caracteres")
        String cpf,

        @NotNull(message = "O status de ativação não pode ser nulo")
        Boolean active,

        @NotNull(message = "O ID da loja não pode ser nulo")
        Long store_id,

        @NotBlank(message = "O e-mail do usuário é obrigatório")
        @Email(message = "O e-mail deve ser válido")
        String email,

        @NotBlank(message = "O nome do usuário é obrigatório")
        String name,

        @NotNull(message = "O telefone do usuário é obrigatório")
        @Pattern(regexp = "^[0-9]+$", message = "O telefone deve conter apenas números")
        String phone,

        @NotBlank(message = "A foto é obrigatória")
        String photo,

        @NotBlank(message = "A senha do usuário é obrigatória")
        @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
        @Pattern(regexp = ".*[A-Z].*", message = "A senha deve conter pelo menos uma letra maiúscula")
        @Pattern(regexp = ".*[a-z].*", message = "A senha deve conter pelo menos uma letra minúscula")
        @Pattern(regexp = ".*\\d.*", message = "A senha deve conter pelo menos um número")
        @Pattern(regexp = ".*[!@#$%^&*(),.?\":{}|<>].*", message = "A senha deve conter pelo menos um caractere especial")
        String password,

        @NotNull(message = "O campo 'role' não pode ser nulo")
        role_enum role
) {}
