package com.agendamais.api.dtos.appointment;

import jakarta.validation.constraints.NotBlank;

public record update_appointment_status_record_dto(
        @NotBlank String status
) {
}
