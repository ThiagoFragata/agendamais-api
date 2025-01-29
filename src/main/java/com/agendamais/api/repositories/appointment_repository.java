package com.agendamais.api.repositories;

import com.agendamais.api.models.appointment_model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface appointment_repository extends JpaRepository<appointment_model, Long> {
    List<appointment_model> findByStoreId(Long store_id);
    List<appointment_model> findByEmployeeId(Long employee_id);
}
