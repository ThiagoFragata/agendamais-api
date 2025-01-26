package com.agendamais.api.repositories;

import com.agendamais.api.models.category_model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface category_repository  extends JpaRepository<category_model, Long> {
}
