package com.agendamais.api.services;

import com.agendamais.api.dtos.category.category_with_store_record_dto;
import com.agendamais.api.models.category_model;
import com.agendamais.api.models.store_model;
import com.agendamais.api.repositories.category_repository;
import com.agendamais.api.repositories.store_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class category_service {

    @Autowired
    private store_repository store_repository;

    @Autowired
    private category_repository category_repository;

    public category_model create_category(category_with_store_record_dto categoryDto) {
        Optional<store_model> storeOpt = store_repository.findById(categoryDto.store_id());

        if (storeOpt.isPresent()) {
            store_model store = storeOpt.get();

            category_model newCategory = new category_model();

            newCategory.setName(categoryDto.category().name());
            newCategory.setActive(categoryDto.category().active());

            newCategory.setStore(store);

            return category_repository.save(newCategory);
        } else {
            throw new RuntimeException("Loja não encontrada");
        }
    }

    public List<category_model> get_all_categories() {
        return category_repository.findAll();
    }

    public Optional<category_model> get_category_by_id(Long categoryId) {
        return category_repository.findById(categoryId);
    }

    public List<category_model> get_categories_by_store(Long storeId) {
        Optional<store_model> storeOpt = store_repository.findById(storeId);

        if (storeOpt.isPresent()) {
            store_model store = storeOpt.get();
            return store.getCategories();
        } else {
            throw new RuntimeException("Loja não encontrada");
        }
    }

    public void delete_category(Long categoryId) {
        Optional<category_model> categoryOpt = category_repository.findById(categoryId);

        if (categoryOpt.isPresent()) {
            category_model category = categoryOpt.get();

            store_model store = category.getStore();
            store.getCategories().remove(category);

            store_repository.save(store);
            category_repository.delete(category);
        } else {
            throw new RuntimeException("Categoria não encontrada");
        }
    }
}
