package com.agendamais.api.controllers;

import com.agendamais.api.dtos.sale.sales_create_record_dto;
import com.agendamais.api.dtos.sale.sales_response_record_dto;
import com.agendamais.api.services.sale_service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Vendas", description = "Endpoints de vendas")
@RestController
@RequestMapping("sales")
public class sale_controller {

    @Autowired
    private sale_service sale_service;

    @Operation(summary = "Criar uma venda")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'EMPLOYEE', 'CUSTOMER')")
    @PostMapping
    public ResponseEntity<sales_response_record_dto> create_sale(@RequestBody sales_create_record_dto sale_dto) {
        sales_response_record_dto response = sale_service.create_sale(sale_dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Buscar todas as vendas")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @GetMapping
    public ResponseEntity<List<sales_response_record_dto>> get_all_sales() {
        List<sales_response_record_dto> sales = sale_service.get_all_sales();
        return ResponseEntity.ok(sales);
    }

    @Operation(summary = "Buscar vendas pelo ID da loja")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    @GetMapping("/store/{id_store}")
    public ResponseEntity<List<sales_response_record_dto>> get_sales_by_store(@PathVariable Long id_store) {
        List<sales_response_record_dto> sales = sale_service.get_sales_by_store(id_store);
        return ResponseEntity.ok(sales);
    }
}
