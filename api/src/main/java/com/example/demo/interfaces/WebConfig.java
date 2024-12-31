package com.example.demo.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@Profile("cors")
@Slf4j
class WebConfig {


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();

        // Allow specific origins
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173"));

        // Allow specific headers (including Authorization, Content-Type, etc.)
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // Allow specific methods (GET, POST, PUT, DELETE, OPTIONS)
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allow credentials if necessary (e.g., cookies, authentication)
        corsConfiguration.setAllowCredentials(true);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        log.info("configured cors: {}", source);

        return source;
    }
}
