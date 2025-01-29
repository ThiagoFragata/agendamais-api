package com.agendamais.api.dtos.sale;

import jakarta.validation.constraints.NotNull;

public record sales_create_record_dto(
        @NotNull(message = "O id do produto é obrigatório")
        Long id_product,

        @NotNull(message = "O id do usuário é obrigatório")
        Long id_user,

        @NotNull(message = "O id da loja é obrigatório")
        Long id_store,

        @NotNull(message = "A quantidade é obrigatória")
        int amount
) {}
