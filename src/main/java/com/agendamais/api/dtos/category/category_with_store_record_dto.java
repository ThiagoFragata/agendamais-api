package com.agendamais.api.dtos.category;

import jakarta.validation.constraints.NotNull;

public record category_with_store_record_dto(
        category_record_dto category,

        @NotNull(message = "O ID da loja é obrigatório")
        Long store_id
) {
}
