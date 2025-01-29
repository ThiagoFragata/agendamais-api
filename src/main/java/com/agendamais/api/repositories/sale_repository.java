package com.agendamais.api.repositories;

import com.agendamais.api.models.sale_model;
import com.agendamais.api.models.store_model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface sale_repository extends JpaRepository<sale_model, Long> {
    List<sale_model> findByStore(store_model store);
    List<sale_model> findByStoreId(Long storeId);
}
