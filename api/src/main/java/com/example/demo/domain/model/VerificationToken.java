package com.example.demo.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
@Getter
@Setter
@Document(collection = "verification_tokens")
public class VerificationToken {
    @Id
    private String id;

    @Indexed(unique = true)
    private String token;

    private String username; // Replace DBRef


    private Instant createdAt;
    private Instant expiresAt;
    private Instant verifiedAt;
    private boolean used = false;

    public VerificationToken(String token, String username, Instant expiresAt) {
        this.token = token;
        this.username = username;
        this.createdAt = Instant.now();
        this.expiresAt = expiresAt;
    }

}