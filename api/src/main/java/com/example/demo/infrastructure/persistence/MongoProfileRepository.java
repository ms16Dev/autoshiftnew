package com.example.demo.infrastructure.persistence;

import com.example.demo.domain.model.UserProfile;
import com.example.demo.domain.repository.ProfileRepository;
import com.example.demo.interfaces.dto.ProfileSummary;
import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MongoProfileRepository implements ProfileRepository {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<UserProfile> findByUsername(String username) {
        return this.mongoTemplate.findOne(query(where("username").is(username)), UserProfile.class);
    }


    @Override
    public Mono<UserProfile> create(UserProfile profile) {
        return this.mongoTemplate.insert(profile);
    }

    @Override
    public Mono<Long> deleteAll() {
        return this.mongoTemplate.remove(UserProfile.class)
                .all()
                .map(DeleteResult::getDeletedCount);
    }

    @Override
    public Flux<UserProfile> findAll() {
        return this.mongoTemplate.findAll(UserProfile.class);
    }

    @Override
    public Mono<UserProfile> save(UserProfile profile) {
        return mongoTemplate.save(profile);
    }

    @Override
    public Flux<ProfileSummary> findByCountry(String country, int offset, int limit) {
        Query query = new Query();

        query.addCriteria(Criteria.where("country").is(country));
        query.addCriteria(Criteria.where("dealer").is(true));


        query.with(Sort.by(Sort.Direction.DESC, "createdDate"));


        // Apply pagination
        query.skip(offset).limit(limit);

        return mongoTemplate
                .find(query, UserProfile.class)
                .map(it ->
                        new ProfileSummary(
                                it.getId(),
                                it.getCoverUrl(),
                                it.getAvatarUrl(),
                                it.getUsername(),
                                it.getName(),
                                it.getSubtitle(),
                                it.getLocation(),
                                it.getLikeCount()
                        )
                );
    }


    @Override
    public Mono<Long> countByCountry(String country) {
        Query query = new Query();

        query.addCriteria(Criteria.where("country").is(country));
        query.addCriteria(Criteria.where("dealer").is(true));


        return mongoTemplate.count(query, UserProfile.class);
    }

}
