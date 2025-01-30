package com.agendamais.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class security_config {

    private final jwt_token_filter jwtTokenFilter;

    public security_config(jwt_token_filter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()                    // Permite acesso público ao endpoint de autenticação
                        .requestMatchers("/admin/**").hasRole("ADMIN")              // Apenas ADMIN pode acessar endpoints de admin
                        .requestMatchers("/owner/**").hasRole("OWNER")              // Apenas OWNER pode acessar endpoints de owner
                        .requestMatchers("/employee/**").hasRole("EMPLOYEE")        // Apenas EMPLOYEE pode acessar endpoints de employee
                        .requestMatchers("/customer/**").hasRole("CUSTOMER")        // Apenas CUSTOMER pode acessar endpoints de customer
                        .anyRequest().authenticated()                                         // Todos os outros endpoints exigem autenticação
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Usa BCrypt para hashear senhas
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}