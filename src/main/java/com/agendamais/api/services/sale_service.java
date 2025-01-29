package com.agendamais.api.services;

import com.agendamais.api.dtos.sale.sales_create_record_dto;
import com.agendamais.api.dtos.sale.sales_response_record_dto;
import com.agendamais.api.models.product_model;
import com.agendamais.api.models.sale_model;
import com.agendamais.api.models.store_model;
import com.agendamais.api.models.user_model;
import com.agendamais.api.repositories.product_repository;
import com.agendamais.api.repositories.sale_repository;
import com.agendamais.api.repositories.store_repository;
import com.agendamais.api.repositories.user_repository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class sale_service {

    @Autowired
    private sale_repository sale_repository;

    @Autowired
    private store_repository store_repository;

    @Autowired
    private product_repository product_repository;

    @Autowired
    private user_repository user_repository;

    @Autowired
    private product_service product_service;

    @Transactional
    public sales_response_record_dto create_sale(sales_create_record_dto sale_dto) {
        product_model product = product_repository.findById(sale_dto.id_product())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        product_service.decrement_product_quantity(sale_dto.id_product(), sale_dto.amount());

        user_model user = user_repository.findById(sale_dto.id_user())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        store_model store = store_repository.findById(sale_dto.id_store())
                .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));

        sale_model sale = new sale_model();
        sale.setProduct(product);
        sale.setUser(user);
        sale.setStore(store);
        sale.setAmount(sale_dto.amount());
        sale.setCreated_at(LocalDateTime.now());

        sale = sale_repository.save(sale);

        return new sales_response_record_dto(
                        sale.getId(),
                        sale.getProduct().getId(),
                        sale.getUser().getId(),
                        sale.getStore().getId(),
                sale.getAmount(),
                sale.getCreated_at()
                );
    }

    public List<sales_response_record_dto> get_all_sales() {
        return sale_repository.findAll().stream()
                .map(sale -> new sales_response_record_dto(
                                        sale.getId(),
                                        sale.getProduct().getId(),
                                        sale.getUser().getId(),
                                        sale.getStore().getId(),
                        sale.getAmount(),
                        sale.getCreated_at()
                                ))
                .toList();
    }

    public List<sales_response_record_dto> get_sales_by_store(Long id_store) {
        store_model store = store_repository.findById(id_store)
                .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));

        return sale_repository.findByStore(store).stream()
                .map(sale -> new sales_response_record_dto(
                                        sale.getId(),
                                        sale.getProduct().getId(),
                                        sale.getUser().getId(),
                                        sale.getStore().getId(),
                        sale.getAmount(),
                        sale.getCreated_at()
                                ))
                .toList();
    }
}
