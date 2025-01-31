package com.agendamais.api.services;

import com.agendamais.api.utils.custom_user_details_util;
import com.agendamais.api.utils.jwt_token_util;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class auth_service {

    private final AuthenticationManager authenticationManager;
    private final jwt_token_util jwtTokenUtil;

    @Lazy
    @Autowired
    private final UserDetailsService userDetailsService;

    public auth_service(
            AuthenticationManager authenticationManager,
            jwt_token_util jwtTokenUtil,
            @Qualifier("custom_user_details_service") UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    public String authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        custom_user_details_util userDetails = (custom_user_details_util) userDetailsService.loadUserByUsername(email);

        Long id = userDetails.getId();
        String name = userDetails.getName();

        return jwtTokenUtil.generateToken(userDetails, id, name);
    }
}