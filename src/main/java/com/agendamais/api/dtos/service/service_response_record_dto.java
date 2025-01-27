package com.agendamais.api.dtos.service;

import com.agendamais.api.dtos.category.category_response_record_dto;
import com.agendamais.api.models.service_model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record service_response_record_dto(
        @NotNull Long id,
        @NotBlank @Size(max = 255) String name,
        @NotNull Double price,
        @NotNull Integer time,
        @NotNull Boolean active,
        category_response_record_dto category
) {
    public service_response_record_dto(service_model service) {
        this(
                service.getId(),
                service.getName(),
                service.getPrice(),
                service.getTime(),
                service.getActive(),
                new category_response_record_dto(service.getCategory())
        );
    }
}