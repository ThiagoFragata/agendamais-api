package com.agendamais.api.dtos.product;

import com.agendamais.api.models.product_model;

public record product_response_dto(
        Long id,
        String name,
        String description,
        Double price,
        int amount,
        String image,
        Long id_store
) {
    public product_response_dto(product_model product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAmount(),
                product.getImage(),
                product.getStore().getId()
        );
    }
}
