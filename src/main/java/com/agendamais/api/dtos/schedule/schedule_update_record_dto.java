package com.agendamais.api.dtos.schedule;

import jakarta.validation.constraints.NotNull;

public record schedule_update_record_dto(
        @NotNull(message = "A disponibilidade é obrigatória")
        Boolean is_available
) {
}
