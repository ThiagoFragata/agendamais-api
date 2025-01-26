package com.agendamais.api.dtos.product;

public record product_create_update_dto(
        String name,
        String description,
        Double price,
        int amount,
        String image
) {}
