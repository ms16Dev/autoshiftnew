package com.example.demo.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebFluxConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:/data/images/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));

        log.info("Configured static resources for /images/** serving from file:/data/images/");
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "https://autoshift-frontend.fly.dev"
        ));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        log.info("Configured WebFlux CORS: {}", corsConfiguration);

        return new CorsWebFilter(source);
    }
}
