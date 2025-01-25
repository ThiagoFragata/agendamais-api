package com.agendamais.api.repositories;

import com.agendamais.api.models.store_model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface store_repository extends JpaRepository<store_model, Long> {
}
