package com.agendamais.api.repositories;

import com.agendamais.api.models.review_model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface review_repository extends JpaRepository<review_model, Long> {
    List<review_model> findByStoreId(Long storeId);
}
