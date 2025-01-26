package com.agendamais.api.services;

import com.agendamais.api.models.product_model;
import com.agendamais.api.models.store_model;
import com.agendamais.api.repositories.product_repository;
import com.agendamais.api.repositories.store_repository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class product_service {

    @Autowired
    private product_repository product_repository;

    @Autowired
    private store_repository store_repository;

    public product_model create_product(Long storeId, product_model product) {
        store_model store = store_repository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));

        product.setStore(store);
        return product_repository.save(product);
    }

    public product_model decrement_product_quantity(Long productId, int quantity) {
        product_model product = product_repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        if (product.getAmount() < quantity) {
            throw new IllegalArgumentException("Quantidade insuficiente");
        }

        product.setAmount(product.getAmount() - quantity);
        return product_repository.save(product);
    }

    public product_model update_product_quantity(Long productId, int quantity) {
        product_model product = product_repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        product.setAmount(quantity);
        return product_repository.save(product);
    }

    public void delete_product(Long productId) {
        product_model product = product_repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        product_repository.delete(product);
    }

    public product_model update_product(Long productId, product_model productDetails) {
        product_model product = product_repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setAmount(productDetails.getAmount());
        product.setImage(productDetails.getImage());

        return product_repository.save(product);
    }


    public List<product_model> get_all_products() {
        return product_repository.findAll();
    }

    public List<product_model> get_products_by_store(Long storeId) {

        store_model store = store_repository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));

        return product_repository.findByStore(store);
    }
}