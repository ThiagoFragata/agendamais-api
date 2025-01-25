package com.agendamais.api.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record address_record_dto(
        @NotBlank(message = "O logradouro é obrigatório")
        String public_place,

        int number,

        @NotBlank(message = "O bairro é obrigatório")
        String neighborhood,

        @NotNull(message = "O longitude é obrigatório")
        int longitude,

        @NotNull(message = "O latitude é obrigatório")
        int latitude
) {}
