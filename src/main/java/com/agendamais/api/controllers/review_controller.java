package com.agendamais.api.controllers;

import com.agendamais.api.dtos.review.review_record_dto;
import com.agendamais.api.dtos.review.review_response_dto;
import com.agendamais.api.models.review_model;
import com.agendamais.api.services.review_service;
import com.agendamais.api.config.success_response_config;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Avaliações", description = "Endpoints de avaliações")
@RestController
@RequestMapping("reviews")
public class review_controller {

    @Autowired
    private review_service review_service;

    @Operation(summary = "Criar uma avaliação")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'EMPLOYEE', 'CUSTOMER')")
    @PostMapping
    public ResponseEntity<success_response_config> create_review(@RequestBody @Valid review_record_dto review_dto) {
        review_model createdReview = review_service.create_review(review_dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new success_response_config("Review criado com sucesso!"));
    }

    @Operation(summary = "Buscar todas as avaliações")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @GetMapping
    public ResponseEntity<List<review_response_dto>> get_all_reviews() {
        List<review_model> reviews = review_service.find_all_reviews();

        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<review_response_dto> response = reviews.stream()
                .map(review_response_dto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar avaliação pelo ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @GetMapping("/{reviewId}")
    public ResponseEntity<review_response_dto> get_review_by_id(@PathVariable Long reviewId) {
        review_model review = review_service.find_review_by_id(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review não encontrado"));

        review_response_dto response_dto = new review_response_dto(review);

        return ResponseEntity.ok(response_dto);
    }

    @Operation(summary = "Buscar avaliações pelo ID da loja")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<review_response_dto>> get_reviews_by_store(@PathVariable Long storeId) {
        List<review_model> reviews = review_service.find_reviews_by_store_id(storeId);

        List<review_response_dto> response = reviews.stream()
                .map(review_response_dto::new)
                .collect(Collectors.toList());

        return response.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }
}
