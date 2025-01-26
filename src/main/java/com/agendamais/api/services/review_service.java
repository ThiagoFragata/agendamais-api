package com.agendamais.api.services;

import com.agendamais.api.dtos.review.review_record_dto;
import com.agendamais.api.models.review_model;
import com.agendamais.api.models.store_model;
import com.agendamais.api.repositories.review_repository;
import com.agendamais.api.repositories.store_repository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class review_service {

    @Autowired
    private review_repository review_repository;

    @Autowired
    private store_repository store_repository;

    public review_model create_review(review_record_dto review_dto) {
        store_model store = store_repository.findById((long) review_dto.id_store()).orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));

        review_model review = new review_model();

        review.setComment(review_dto.comment());
        review.setStars(review_dto.stars());
        review.setStore(store);

        return review_repository.save(review);
    }

    public List<review_model> find_all_reviews() {
        return review_repository.findAll();
    }

    public Optional<review_model> find_review_by_id(Long reviewId) {
        return review_repository.findById(reviewId);
    }

    public List<review_model> find_reviews_by_store_id(Long storeId) {

        if (!store_repository.existsById(storeId)) {
            throw new EntityNotFoundException("Loja não encontrada");
        }

        return review_repository.findByStoreId(storeId);
    }
}
