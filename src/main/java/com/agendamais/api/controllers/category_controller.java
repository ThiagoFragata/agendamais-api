package com.agendamais.api.controllers;

import com.agendamais.api.dtos.category.category_record_dto;
import com.agendamais.api.dtos.category.category_with_store_record_dto;
import com.agendamais.api.models.category_model;
import com.agendamais.api.services.category_service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "Categorias", description = "Endpoints de categorias")
@RestController
@RequestMapping("categories")
public class category_controller {

    @Autowired
    private category_service categoryService;

    @Operation(summary = "Criar categoria")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @PostMapping("/store/{storeId}")
    public ResponseEntity<category_model> create_category(
            @PathVariable Long storeId,
            @RequestBody @Valid category_record_dto category) {

        category_with_store_record_dto categoryWithStoreDto = new category_with_store_record_dto(category, storeId);

        category_model savedCategory = categoryService.create_category(categoryWithStoreDto);
        return ResponseEntity.ok(savedCategory);
    }

    @Operation(summary = "Buscar todas as categorias")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<category_model>> get_all_categories() {
        List<category_model> categories = categoryService.get_all_categories();

        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Buscar categoria pelo ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @GetMapping("/{categoryId}")
    public ResponseEntity<Object> get_category_by_id(@PathVariable Long categoryId) {
        Optional<category_model> category = categoryService.get_category_by_id(categoryId);

        if (category.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Categoria não encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(category.get());
    }

    @Operation(summary = "Buscar categorias pelo ID da loja")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'EMPLOYEE', 'CUSTOMER')")
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<category_model>> get_categories_by_store(@PathVariable Long storeId) {
        List<category_model> categories = categoryService.get_categories_by_store(storeId);
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Deletar categoria")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Map<String, String>> delete_category(@PathVariable Long categoryId) {
        categoryService.delete_category(categoryId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Categoria deletada com sucesso");

        return ResponseEntity.ok(response);
    }
}