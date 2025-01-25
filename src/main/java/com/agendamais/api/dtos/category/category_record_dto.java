package com.agendamais.api.dtos.category;

import jakarta.validation.constraints.NotBlank;

public record category_record_dto(

        Long id,

        @NotBlank(message = "O nome da categoria é obrigatório")
        String name,

        boolean active
) {}
