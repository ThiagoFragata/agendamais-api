package com.agendamais.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class cors_config {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000"); // Adicione as origens necessárias
        configuration.addAllowedOrigin("http://localhost:8080");   // Mais origens
        configuration.addAllowedOrigin("http://192.168.0.124:8080");   // Mais origens
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // Métodos permitidos
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization")); // Cabeçalhos permitidos
        configuration.setAllowCredentials(true); // Se precisar de cookies ou autenticação
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Configura para todas as rotas
        return source;
    }
}

