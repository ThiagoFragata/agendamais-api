package com.agendamais.api.services;

import com.agendamais.api.dtos.user.user_response_record_dto;
import com.agendamais.api.utils.custom_user_details;
import com.agendamais.api.security.jwt_token_util;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class auth_service {

    private final AuthenticationManager authenticationManager;
    private final jwt_token_util jwtTokenUtil;

    @Lazy
    @Autowired
    private final UserDetailsService userDetailsService;

    @Lazy
    @Autowired
    private user_service user_service;

    public auth_service(
            AuthenticationManager authenticationManager,
            jwt_token_util jwtTokenUtil,
            @Qualifier("custom_user_details_service") UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    public String authenticate(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            custom_user_details userDetails = (custom_user_details) userDetailsService.loadUserByUsername(email);

            if (!userDetails.isEnabled()) {
                throw new DisabledException("A conta está desativada. Contate o administrador.");
            }

            Long id = userDetails.getId();
            String name = userDetails.getName();

            Optional<user_response_record_dto> userOptional = user_service.find_user_by_id(id);

            if (userOptional.isEmpty()) {
                throw new UsernameNotFoundException("Usuário não encontrado.");
            }

            String role = String.valueOf(userOptional.get().role());

            return jwtTokenUtil.generateToken(userDetails, id, name, role);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciais inválidas. Verifique seu email e senha.");
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Usuário não encontrado. Verifique seu email.");
        }
    }
}
