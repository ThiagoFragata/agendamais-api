package com.agendamais.api.repositories;

import com.agendamais.api.models.employee_model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface employee_repository extends JpaRepository<employee_model, Long> {
    List<employee_model> findByStoreId(Long storeId);
}
