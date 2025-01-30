package com.agendamais.api.controllers;

import com.agendamais.api.dtos.user.user_record_dto;
import com.agendamais.api.services.auth_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class auth_controller {

    @Autowired
    private auth_service authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody user_record_dto request) {
        String token = authService.authenticate(request.email(), request.password());
        return ResponseEntity.ok(token);
    }
}
