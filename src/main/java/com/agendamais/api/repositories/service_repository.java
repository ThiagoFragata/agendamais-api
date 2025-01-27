package com.agendamais.api.repositories;

import com.agendamais.api.models.service_model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface service_repository extends JpaRepository<service_model, Long> {
    List<service_model> findByStoreId(Long storeId);
}
