package com.agendamais.api.dtos.schedule;

import com.agendamais.api.models.schedule_model;

public record schedule_create_response_record_dto(
        Long id,
        String schedule,
        boolean available,
        Long employee_id
) {
    public schedule_create_response_record_dto(schedule_model schedule) {
        this(
                schedule.getId(),
                schedule.getSchedule(),
                schedule.getAvailable(),
                schedule.getEmployee().getId()
        );
    }
}