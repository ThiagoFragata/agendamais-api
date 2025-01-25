package com.agendamais.api.services;

import com.agendamais.api.dtos.user.address_record_dto;
import com.agendamais.api.dtos.user.store_record_dto;
import com.agendamais.api.dtos.user.store_with_address_record_dto;
import com.agendamais.api.models.address_model;
import com.agendamais.api.models.store_model;
import com.agendamais.api.repositories.address_repository;
import com.agendamais.api.repositories.store_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class store_service {

    @Autowired
    private store_repository store_repository;

    @Autowired
    private address_repository address_repository;

    public store_with_address_record_dto create_store_with_address(store_with_address_record_dto store) {

        address_model new_address = new address_model();

        new_address.setPublic_place(store.address().public_place());
        new_address.setNumber(store.address().number());
        new_address.setNeighborhood(store.address().neighborhood());
        new_address.setLongitude(store.address().longitude());
        new_address.setLatitude(store.address().latitude());

        address_model saved_address = address_repository.save(new_address);

        store_model new_store = new store_model();
        new_store.setName(store.store().name());
        new_store.setAbout(store.store().about());
        new_store.setPhone(store.store().phone());
        new_store.setAddress(saved_address);

        store_model saved_store = store_repository.save(new_store);

        store_record_dto store_dto = new store_record_dto(
                saved_store.getName(),
                saved_store.getAbout(),
                saved_store.getPhone()
        );

        address_record_dto address_dto = new address_record_dto(
                saved_address.getPublic_place(),
                saved_address.getNumber(),
                saved_address.getNeighborhood(),
                saved_address.getLongitude(),
                saved_address.getLatitude()
        );

        return new store_with_address_record_dto(store_dto, address_dto);
    }


    public void update_store_address(Long store_id, address_record_dto new_address) {

        Optional<store_model> storeOpt = store_repository.findById(store_id);

        if (storeOpt.isPresent()) {
            store_model store = storeOpt.get();

            address_model address = store.getAddress();

            if (address != null) {
                address.setPublic_place(new_address.public_place());
                address.setNumber(new_address.number());
                address.setNeighborhood(new_address.neighborhood());
                address.setLongitude(new_address.longitude());
                address.setLatitude(new_address.latitude());

                address_repository.save(address);
            } else {
                throw new RuntimeException("Endereço não encontrado para a loja");
            }

            store_repository.save(store);
        } else {
            throw new RuntimeException("Loja não encontrada");
        }
    }

    public address_model get_store_address(Long store_id) {
        Optional<store_model> storeOpt = store_repository.findById(store_id);

        if (storeOpt.isPresent()) {
            return storeOpt.get().getAddress();
        } else {
            throw new RuntimeException("Loja não encontrada");
        }
    }

    public List<store_model> find_all_stores() {
        return store_repository.findAll();
    }

    public Optional<store_model> find_store_by_id(Long id) {
        return store_repository.findById(id);
    }

}
