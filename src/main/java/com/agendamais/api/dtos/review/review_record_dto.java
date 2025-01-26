package com.agendamais.api.dtos.review;

import jakarta.validation.constraints.NotBlank;

public record review_record_dto(
        @NotBlank(message = "O campo comentario é obrigatório")
        String comment,

        int stars,

        int id_store
) {}
