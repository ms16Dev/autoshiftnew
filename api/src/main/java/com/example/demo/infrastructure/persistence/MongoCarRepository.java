package com.example.demo.infrastructure.persistence;

import com.example.demo.domain.model.Comment;
import com.example.demo.domain.model.Car;
import com.example.demo.domain.model.Status;
import com.example.demo.domain.repository.CarRepository;
import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

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
    public Mono<Long> countByKeyword(String keyword) {
        return mongoTemplate.count(query(where("title").regex(".*" + keyword + ".*", "i")), Car.class);
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
    public Mono<Boolean> updateStatus(String id, Status status) {
        return mongoTemplate.update(Car.class)
                .matching(where("id").is(id))
                .apply(Update.update("status", status))
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
        var comment = mongoTemplate.insert(Comment.builder().content(content).build());
        return comment.flatMap(c -> mongoTemplate.update(Car.class)
                .matching(where("id").is(id))
                .apply(new Update().push("comments", c))
                .all()
                .map(result -> result.getModifiedCount() == 1L)
        );
    }

    @Override
    public Mono<Boolean> removeComment(String id, String commentId) {
        var comment = mongoTemplate.findById(commentId, Comment.class);
        return comment.flatMap(c -> mongoTemplate.update(Car.class)
                .matching(where("id").is(id))
                .apply(new Update().pull("comments", c))
                .all()
                .map(result -> result.getModifiedCount() == 1L)
        );
    }
}
