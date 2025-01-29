package com.agendamais.api.controllers;

import com.agendamais.api.dtos.product.product_create_update_dto;
import com.agendamais.api.dtos.product.product_response_dto;
import com.agendamais.api.models.product_model;
import com.agendamais.api.services.product_service;
import com.agendamais.api.config.success_response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("products")
public class product_controller {

    @Autowired
    private product_service product_service;

    @PostMapping("/store/{storeId}")
    public ResponseEntity<product_response_dto> create_product(@PathVariable Long storeId, @RequestBody @Valid product_create_update_dto productDto) {
        product_model product = new product_model();
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        product.setAmount(productDto.amount());
        product.setImage(productDto.image());

        product_model savedProduct = product_service.create_product(storeId, product);
        product_response_dto response = new product_response_dto(savedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<product_response_dto>> get_all_products() {
        List<product_model> products = product_service.get_all_products();
        List<product_response_dto> response = products.stream()
                .map(product_response_dto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<product_response_dto>> get_products_by_store(@PathVariable Long storeId) {
        List<product_model> products = product_service.get_products_by_store(storeId);
        List<product_response_dto> response = products.stream()
                .map(product_response_dto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<product_response_dto> update_product(@PathVariable Long productId, @RequestBody @Valid product_create_update_dto productDto) {
        product_model productDetails = new product_model();
        productDetails.setName(productDto.name());
        productDetails.setDescription(productDto.description());
        productDetails.setPrice(productDto.price());
        productDetails.setAmount(productDto.amount());
        productDetails.setImage(productDto.image());

        product_model updatedProduct = product_service.update_product(productId, productDetails);
        product_response_dto response = new product_response_dto(updatedProduct);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}/decrement/{quantity}")
    public ResponseEntity<product_response_dto> decrement_product_quantity(@PathVariable Long productId, @PathVariable int quantity) {
        product_model updatedProduct = product_service.decrement_product_quantity(productId, quantity);
        product_response_dto response = new product_response_dto(updatedProduct);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}/quantity/{quantity}")
    public ResponseEntity<product_response_dto> update_product_quantity(@PathVariable Long productId, @PathVariable int quantity) {
        product_model updatedProduct = product_service.update_product_quantity(productId, quantity);
        product_response_dto response = new product_response_dto(updatedProduct);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<success_response> delete_product(@PathVariable Long productId) {
        product_service.delete_product(productId);

        success_response successResponse = new success_response("Produto deletado com sucesso");

        return ResponseEntity.ok(successResponse);
    }
}
