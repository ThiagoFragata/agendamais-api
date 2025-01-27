package com.agendamais.api.dtos.address;

import com.agendamais.api.models.address_model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record address_response_record_dto(
        @NotNull Long id,
        @NotBlank @Size(max = 255) String public_place,
        @NotNull Integer number,
        @NotBlank @Size(max = 255) String neighborhood,
        @NotNull int longitude,
        @NotNull int latitude
) {
    public address_response_record_dto(address_model address) {
        this(
                address.getId(),
                address.getPublic_place(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getLongitude(),
                address.getLatitude()
        );
    }
}

