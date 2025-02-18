package com.example.demo.infrastructure.persistence;

import com.example.demo.domain.model.Car;
import com.example.demo.domain.model.Comment;
import com.example.demo.domain.repository.CarRepository;
import com.example.demo.interfaces.dto.CarSummary;
import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MongoCarRepository implements CarRepository {
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Flux<Car> findAll() {
        return mongoTemplate.findAll(Car.class);
    }

    @Override
    public Flux<CarSummary> findByKeyword(String keyword, int offset, int limit) {
        Query query = new Query();

        // Apply keyword filter only if provided
        if (keyword != null && !keyword.isBlank()) {
            query.addCriteria(Criteria.where("title").regex(".*" + keyword + ".*", "i"));
        }

        // Apply pagination
        query.skip(offset).limit(limit);

        return mongoTemplate
                .find(query, Car.class)
                .map(it ->
                        new CarSummary(
                                it.getId(),
                                it.getCoverImage(),
                                it.getPrice(),
                                it.getMake(),
                                it.getMakeUrl(),
                                it.getModel(),
                                it.getOrigin(),
                                it.getYear(),
                                it.getSeenCount(),
                                it.getLikeCount(),
                                it.getShareCount(),
                                it.getComments().size(),
                                it.getDescription(),
                                it.getCreatedBy(),
                                it.getCreatedDate()
                        )
                );
    }


    @Override
    public Mono<Long> countByKeyword(String keyword) {
        Query query = new Query();

        // Apply keyword filter only if provided
        if (keyword != null && !keyword.isBlank()) {
            query.addCriteria(Criteria.where("title").regex(".*" + keyword + ".*", "i"));
        }

        return mongoTemplate.count(query, Car.class);
    }

    @Override
    public Mono<Car> findById(String id) {
        return mongoTemplate.findById(id, Car.class);
    }

    @Override
    public Mono<Car> create(Car car) {
        return mongoTemplate.insert(car);
    }

    @Override
    public Mono<Boolean> update(String id, String title, String content) {
        return mongoTemplate.update(Car.class)
                .matching(where("id").is(id))
                .apply(Update.update("title", title).set("content", content))
                .all()
                .map(result -> result.getModifiedCount() == 1L);
    }


    @Override
    public Mono<Boolean> updateStars(String id, int stars) {
        return mongoTemplate.update(Car.class)
                .matching(where("id").is(id))
                .apply(Update.update("stars", stars))
                .all()
                .map(result -> result.getModifiedCount() == 1L);
    }

    @Override
    public Mono<Boolean> deleteById(String id) {
        return mongoTemplate.remove(Car.class)
                .matching(where("id").is(id))
                .all()
                .map(result -> result.getDeletedCount() == 1L);
    }

    @Override
    public Mono<Long> deleteAll() {
        return mongoTemplate.remove(Car.class)
                .all()
                .map(DeleteResult::getDeletedCount);
    }

    @Override
    public Mono<Boolean> addComment(String id, String content) {
        return mongoTemplate.insert(Comment.builder().content(content).build()) // Insert the comment
                .map(Comment::getId) // Extract the comment ID
                .flatMap(commentId -> mongoTemplate.update(Car.class)
                        .matching(where("id").is(id))
                        .apply(new Update().push("comments", commentId)) // Push only the ID
                        .all()
                        .map(result -> result.getModifiedCount() == 1L)
                );
    }

    @Override
    public Mono<Boolean> removeComment(String carId, String commentId) {
        return mongoTemplate.updateFirst(
                Query.query(Criteria.where("id").is(carId)),
                new Update().pull("comments", commentId), // Remove the ID, not the whole object
                Car.class
        ).flatMap(updateResult ->
                mongoTemplate.remove(Query.query(Criteria.where("id").is(commentId)), Comment.class)
                        .map(deleteResult -> updateResult.getModifiedCount() == 1L && deleteResult.getDeletedCount() == 1L)
        );
    }


    @Override
    public Mono<Boolean> visitCar(String id) {
        return mongoTemplate.updateFirst(
                Query.query(Criteria.where("id").is(id)),
                new Update().inc("seenCount", 1),
                Car.class
        ).map(updateResult -> updateResult.getModifiedCount() == 1L);
    }

    @Override
    public Mono<Boolean> likeCar(String carId, String username) {
        return mongoTemplate.updateFirst(
                Query.query(Criteria.where("id").is(carId).and("likedBy").not().elemMatch(Criteria.where("$eq").is(username))),
                new Update().inc("likeCount", 1).push("likedBy", username),
                Car.class
        ).map(updateResult -> updateResult.getModifiedCount() == 1L);
    }

    @Override
    public Mono<Boolean> unLikeCar(String carId, String username) {
        return mongoTemplate.updateFirst(
                Query.query(Criteria.where("id").is(carId).and("likedBy").is(username)),
                new Update().inc("likeCount", -1).pull("likedBy", username),
                Car.class
        ).map(updateResult -> updateResult.getModifiedCount() == 1L);
    }

    @Override
    public Mono<Boolean> hasLikedCar(String carId, String username) {
        return mongoTemplate.exists(
                Query.query(Criteria.where("id").is(carId).and("likedBy").is(username)),
                Car.class
        );
    }

    @Override
    public Mono<Boolean> shareCar(String carId, String username) {
        return mongoTemplate.updateFirst(
                Query.query(Criteria.where("id").is(carId).and("sharedBy").not().elemMatch(Criteria.where("$eq").is(username))),
                new Update().inc("shareCount", 1).push("sharedBy", username),
                Car.class
        ).map(updateResult -> updateResult.getModifiedCount() == 1L);
    }
}
