package com.agendamais.api.repositories;

import com.agendamais.api.models.address_model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface address_repository extends JpaRepository<address_model, Long> {
}
