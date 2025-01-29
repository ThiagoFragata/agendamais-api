package com.agendamais.api.dtos.appointment;

import com.agendamais.api.models.appointment_model;

public record appointment_response_record_dto(
        Long id,
        String schedule_time,
        String status,
        String user_name,
        String employee_name,
        String service_name,
        Double service_price,
        String store_name
) {
    public appointment_response_record_dto(appointment_model appointment) {
        this(
                appointment.getId(),
                appointment.getSchedule().getSchedule(),
                appointment.getStatus().toString(),
                appointment.getUser().getName(),
                appointment.getEmployee().getUser().getName(),
                appointment.getService().getName(),
                appointment.getService().getPrice(),
                appointment.getStore().getName()
        );
    }
}

