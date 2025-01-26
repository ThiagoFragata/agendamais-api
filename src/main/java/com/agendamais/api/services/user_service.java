package com.agendamais.api.services;

import com.agendamais.api.dtos.user.user_record_dto;
import com.agendamais.api.dtos.user.user_response_record_dto;
import com.agendamais.api.models.user_model;
import com.agendamais.api.repositories.user_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

@Service
public class user_service {

    @Autowired
    private  user_repository user_repository;

    public user_model create_user(user_record_dto user_record_dto) {
        user_model user = new user_model();

        if (user_repository.existsByEmail(user_record_dto.email())) {
            throw new IllegalArgumentException("O e-mail fornecido já está em uso.");
        }


        if (user_repository.existsByPhone(user_record_dto.phone())) {
            throw new IllegalArgumentException("O telefone fornecido já está em uso.");
        }

        user.setEmail(user_record_dto.email());
        user.setName(user_record_dto.name());
        user.setPhone(user_record_dto.phone());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user_record_dto.password());
        user.setPassword(encodedPassword);

        try {
            return user_repository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Erro ao salvar o usuário, por favor tente novamente.");
        }
    }

    public List<user_response_record_dto> find_all_users() {
        return user_repository.findAll().stream()
                .map(user -> new user_response_record_dto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhone()
                ))
                .collect(Collectors.toList());
    }

    public Optional<user_response_record_dto> find_user_by_id(Long id) {
        return user_repository.findById(id)
                .map(user -> new user_response_record_dto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhone()
                ));
    }

    public void delete_user(Long id) {
        if (user_repository.existsById(id)) {
            user_repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
    }

}
