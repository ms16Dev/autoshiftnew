package com.example.demo.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;

@Configuration
public class SessionConfig {

    @Bean
    public WebSessionIdResolver webSessionIdResolver() {
        CookieWebSessionIdResolver resolver = new CookieWebSessionIdResolver();

        resolver.addCookieInitializer(builder -> {
            builder.maxAge(3600);       // ⏳ 1 hour expiration
            builder.sameSite("None");   // 🔥 Necessary to allow cross-site cookie
            builder.secure(true);       // 🔒 Required for SameSite=None
            builder.path("/");
            builder.httpOnly(true);     // Optional but recommended
        });


        return resolver;
    }
}
