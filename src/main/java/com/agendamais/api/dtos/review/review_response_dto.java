package com.agendamais.api.dtos.review;

import com.agendamais.api.models.review_model;

public record review_response_dto(
        Long id,
        String comment,
        Integer stars,
        Long id_store
) {
    public review_response_dto(review_model review) {
        this(
                review.getId(),
                review.getComment(),
                review.getStars(),
                review.getStore() != null ? review.getStore().getId() : null
        );
    }
}
