package com.agendamais.api.dtos.category;

import com.agendamais.api.models.category_model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record category_response_record_dto(
        @NotNull Long id,
        @NotBlank @Size(max = 255) String name,
        @NotNull Boolean active
) {
    public category_response_record_dto(category_model category) {
        this(category.getId(), category.getName(), category.getActive());
    }
}

