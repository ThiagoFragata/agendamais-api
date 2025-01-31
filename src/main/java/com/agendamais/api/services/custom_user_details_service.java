package com.agendamais.api.services;

import com.agendamais.api.repositories.user_repository;
import com.agendamais.api.utils.custom_user_details;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class custom_user_details_service implements UserDetailsService {

    private final user_repository userRepository;

    public custom_user_details_service(user_repository userRepository, @Lazy user_service userService) {
        this.userRepository = userRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> new custom_user_details(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPassword(),
                        Collections.emptyList()
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    }
}