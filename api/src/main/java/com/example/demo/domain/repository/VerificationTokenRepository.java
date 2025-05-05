package com.example.demo.domain.repository;

import com.example.demo.domain.model.User;
import com.example.demo.domain.model.VerificationToken;
import reactor.core.publisher.Mono;

import java.time.Instant;

public interface VerificationTokenRepository {
    Mono<VerificationToken> findByToken(String token);
    Mono<VerificationToken> findByUsernameAndUsedFalse(String username);
    Mono<Long> deleteExpiredTokens(Instant now);
    Mono<VerificationToken> save(VerificationToken token);
    Mono<Void> delete(VerificationToken token);
}