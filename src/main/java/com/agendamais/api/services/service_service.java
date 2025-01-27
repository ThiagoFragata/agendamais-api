package com.agendamais.api.services;

import com.agendamais.api.dtos.service.service_create_update_record_dto;
import com.agendamais.api.models.category_model;
import com.agendamais.api.models.service_model;
import com.agendamais.api.models.store_model;
import com.agendamais.api.repositories.category_repository;
import com.agendamais.api.repositories.service_repository;
import com.agendamais.api.repositories.store_repository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class service_service {

    @Autowired
    private service_repository service_repository;

    @Autowired
    private store_repository store_repository;

    @Autowired
    private category_repository category_repository;


    public service_model create_service(service_create_update_record_dto serviceDto) {

        store_model store = store_repository.findById(serviceDto.store_id())
                .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));

        category_model category = category_repository.findById(serviceDto.category_id())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        if (!store.getCategories().contains(category)) {
            throw new IllegalArgumentException("A categoria fornecida não pertence à loja selecionada.");
        }

        service_model service = new service_model();
        service.setName(serviceDto.name());
        service.setPrice(serviceDto.price());
        service.setTime(serviceDto.time());
        service.setActive(true);
        service.setStore(store);
        service.setCategory(category);

        return service_repository.save(service);
    }

    public List<service_model> get_all_services() {
        return service_repository.findAll();
    }

    public List<service_model> get_services_by_store(Long storeId) {
        return service_repository.findByStoreId(storeId);
    }

    public service_model get_service_by_id(Long id) {
        return service_repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));
    }

    public service_model update_service(Long id, service_create_update_record_dto dto) {
        service_model service = get_service_by_id(id);

        store_model store = store_repository.findById(dto.store_id())
                .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));

        category_model category = category_repository.findById(dto.category_id())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        service.setName(dto.name());
        service.setPrice(dto.price());
        service.setTime(dto.time());
        service.setActive(dto.active());
        service.setStore(store);
        service.setCategory(category);

        return service_repository.save(service);
    }

    public void deactivate_service(Long id) {
        service_model service = get_service_by_id(id);
        service.setActive(false);
        service_repository.save(service);
    }

    public void activate_service(Long id) {
        service_model service = get_service_by_id(id);
        service.setActive(true);
        service_repository.save(service);
    }
}
