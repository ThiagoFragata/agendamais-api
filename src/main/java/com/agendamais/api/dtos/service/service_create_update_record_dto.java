package com.agendamais.api.dtos.service;

import jakarta.validation.constraints.*;

import java.util.List;

public record service_create_update_record_dto(
        @NotBlank(message = "O nome do serviço é obrigatório")
        @Size(max = 100, message = "O nome do serviço deve ter no máximo 100 caracteres")
        String name,

        @NotNull(message = "O preço do serviço é obrigatório")
        @DecimalMin(value = "0.0", inclusive = false, message = "O preço do serviço deve ser maior que zero")
        @Digits(integer = 10, fraction = 2, message = "O preço do serviço deve ter no máximo 10 dígitos inteiros e 2 decimais")
        Double price,

        @NotNull(message = "O tempo do serviço é obrigatório")
        @Min(value = 1, message = "O tempo do serviço deve ser no mínimo 1 minuto")
        Integer time,

        @NotNull(message = "O status do serviço (ativo ou inativo) é obrigatório")
        Boolean active,

        @NotNull(message = "O ID da categoria é obrigatório")
        Long category_id,

        @NotNull(message = "O ID da loja é obrigatório")
        Long store_id,

        List<Long> employee_ids
) {}
