package com.agendamais.api.controllers;

import com.agendamais.api.dtos.sale.sales_create_record_dto;
import com.agendamais.api.dtos.sale.sales_response_record_dto;
import com.agendamais.api.services.sale_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sales")
public class sale_controller {

    @Autowired
    private sale_service sale_service;

    @PostMapping
    public ResponseEntity<sales_response_record_dto> create_sale(@RequestBody sales_create_record_dto sale_dto) {
        sales_response_record_dto response = sale_service.create_sale(sale_dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<sales_response_record_dto>> get_all_sales() {
        List<sales_response_record_dto> sales = sale_service.get_all_sales();
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/store/{id_store}")
    public ResponseEntity<List<sales_response_record_dto>> get_sales_by_store(@PathVariable Long id_store) {
        List<sales_response_record_dto> sales = sale_service.get_sales_by_store(id_store);
        return ResponseEntity.ok(sales);
    }
}
