package com.example.demo.application.services;

import com.example.demo.domain.exception.InvalidTokenException;
import com.example.demo.domain.exception.TokenExpiredException;
import com.example.demo.domain.model.User;
import com.example.demo.domain.model.VerificationToken;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.domain.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationTokenService {
    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    public Mono<VerificationToken> generateVerificationToken(User user) {
        // Invalidate any existing tokens for this user
        return tokenRepository.findByUserAndUsedFalse(user)
                .flatMap(token -> tokenRepository.delete(token).thenReturn(token))
                .then(Mono.defer(() -> {
                    // Create new token after ensuring previous is deleted
                    String tokenValue = UUID.randomUUID().toString();
                    Instant expiryDate = Instant.now().plus(24, ChronoUnit.HOURS);
                    VerificationToken token = new VerificationToken(tokenValue, user, expiryDate);
                    return tokenRepository.save(token);
                }));
    }

    public Mono<User> verifyUser(String tokenValue) {
        return tokenRepository.findByToken(tokenValue)
                .switchIfEmpty(Mono.error(new InvalidTokenException("Token not found")))
                .flatMap(token -> {
                    if (token.isUsed()) {
                        return Mono.error(new InvalidTokenException("Token already used"));
                    }

                    if (Instant.now().isAfter(token.getExpiresAt())) {
                        return Mono.error(new TokenExpiredException("Token expired"));
                    }

                    // Mark token as used
                    token.setUsed(true);
                    token.setVerifiedAt(Instant.now());

                    return tokenRepository.save(token)
                            .flatMap(savedToken -> {
                                // Verify the user
                                User user = savedToken.getUser();
                                user.setVerified(true);
                                return userRepository.save(user);
                            });
                });
    }

    @Scheduled(cron = "0 0 3 * * ?") // Daily cleanup
    public Mono<Long> cleanupExpiredTokens() {
        return tokenRepository.deleteExpiredTokens(Instant.now())
                .doOnNext(count ->
                        log.debug("Cleaned up {} expired verification tokens", count)
                );
    }
}