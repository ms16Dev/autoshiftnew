package com.example.demo.infrastructure.persistence;

import com.example.demo.domain.model.User;
import com.example.demo.domain.model.VerificationToken;
import com.example.demo.domain.repository.VerificationTokenRepository;
import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MongoVerificationTokenRepository implements VerificationTokenRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<VerificationToken> findByToken(String token) {
        return mongoTemplate.findOne(
                query(where("token").is(token)),
                VerificationToken.class
        );
    }

    @Override
    public Mono<VerificationToken> findByUserAndUsedFalse(User user) {
        return mongoTemplate.findOne(
                query(where("user.$id").is(user.getId())
                        .and("used").is(false)),
                VerificationToken.class
        );
    }

    @Override
    public Mono<Long> deleteExpiredTokens(Instant now) {
        return mongoTemplate.remove(
                query(where("expiresAt").lt(now)),
                VerificationToken.class
        ).map(DeleteResult::getDeletedCount);
    }

    @Override
    public Mono<VerificationToken> save(VerificationToken token) {
        return mongoTemplate.save(token);
    }

    @Override
    public Mono<Void> delete(VerificationToken token) {
        return mongoTemplate.remove(token).then();
    }
}
