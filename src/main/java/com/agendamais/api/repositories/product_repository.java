package com.agendamais.api.repositories;

import com.agendamais.api.models.product_model;
import com.agendamais.api.models.store_model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface product_repository extends JpaRepository<product_model, Long> {
    List<product_model> findByStore(store_model store);
}
