package com.agendamais.api.dtos.appointment;

import com.agendamais.api.config.appointment_status_enum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record appointment_create_record_dto(
        @NotNull(message = "O ID do horário é obrigatório")
        @Min(value = 1, message = "O ID do horário deve ser válido")
        Long schedule_id,

        @NotNull(message = "O ID do usuário é obrigatório")
        @Min(value = 1, message = "O ID do usuário deve ser válido")
        Long user_id,

        @NotNull(message = "O ID do funcionário é obrigatório")
        @Min(value = 1, message = "O ID do funcionário deve ser válido")
        Long employee_id,

        @NotNull(message = "O ID do serviço é obrigatório")
        @Min(value = 1, message = "O ID do serviço deve ser válido")
        Long service_id,

        @NotNull(message = "O ID da loja é obrigatório")
        @Min(value = 1, message = "O ID da loja deve ser válido")
        Long store_id,

        @NotNull(message = "O status do agendamento é obrigatório")
        appointment_status_enum status
) {
}
