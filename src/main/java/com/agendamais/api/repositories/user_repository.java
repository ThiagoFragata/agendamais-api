package com.agendamais.api.repositories;

import com.agendamais.api.models.user_model;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface user_repository extends JpaRepository<user_model, Long> {
    boolean existsByEmail(@NotBlank(message = "O email é obrigatório") @Email(message = "O email deve ser válido") String email);

    boolean existsByPhone(@NotNull(message = "O telefone é obrigatório") @Pattern(regexp = "^[0-9]+$", message = "O telefone deve conter apenas números") String phone);
}
