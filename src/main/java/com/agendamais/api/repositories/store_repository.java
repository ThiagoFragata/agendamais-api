package com.agendamais.api.repositories;

import com.agendamais.api.models.store_model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface store_repository extends JpaRepository<store_model, Long> {
    List<store_model> findByCategories_Id(Long categoryId);
}
