package com.agendamais.api.dtos.sale;

import java.time.LocalDateTime;

public record sales_response_record_dto(
        Long id,
        Long id_product,
        Long id_user,
        Long id_store,
        int amount,
        LocalDateTime created_at
) {}
