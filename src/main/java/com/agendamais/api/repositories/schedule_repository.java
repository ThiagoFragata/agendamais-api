package com.agendamais.api.repositories;

import com.agendamais.api.models.schedule_model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface schedule_repository extends JpaRepository<schedule_model, Long> {
    List<schedule_model> findByEmployeeIdOrderByIdAsc(Long employeeId);
    void deleteByEmployeeId(Long employeeId);
}
