package com.agendamais.api.dtos.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record store_record_dto(
        @NotBlank(message = "O nome da loja é obrigatório")
        String name,

        String about,

        @NotNull(message = "O telefone é obrigatório")
        Integer phone
) {
}
