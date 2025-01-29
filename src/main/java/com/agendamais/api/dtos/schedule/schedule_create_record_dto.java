package com.agendamais.api.dtos.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record schedule_create_record_dto(
        @NotBlank(message = "O horário inicial é obrigatório")
        String start_time,

        @NotBlank(message = "O horário final é obrigatório")
        String end_time,

        @NotNull(message = "A duração é obrigatória")
        @Positive(message = "A duração deve ser maior que 0")
        Integer duration,

        @NotNull(message = "O ID do funcionário é obrigatório")
        Long employee_id
) {
}
