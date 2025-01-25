package com.agendamais.api.services;

import com.agendamais.api.dtos.address.address_record_dto;
import com.agendamais.api.models.address_model;
import com.agendamais.api.repositories.address_repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class address_service {

    @Autowired
    private address_repository address_repository;

    public address_model create_address(address_record_dto address_create_record_dto) {
        address_model address = new address_model();

        try {
            address.setPublic_place(address_create_record_dto.public_place());
            address.setNumber(address_create_record_dto.number());
            address.setNeighborhood(address_create_record_dto.neighborhood());
            address.setLatitude(address_create_record_dto.latitude());
            address.setLongitude(address_create_record_dto.longitude());

            return address_repository.save(address);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Erro ao salvar o endereço, por favor tente novamente.");
        }
    }

    public List<address_model> find_all_address() {
        return address_repository.findAll();
    }

    public Optional<address_model> find_address_by_id(Long store_id) {
        return address_repository.findById(store_id);
    }

    public void delete_address(Long id) {
        if (address_repository.existsById(id)) {
            address_repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Endereço não encontrado.");
        }
    }

}
