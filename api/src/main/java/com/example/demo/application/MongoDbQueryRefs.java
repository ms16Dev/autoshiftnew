package com.example.demo.application;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class MongoDbQueryRefs {

    private final ReactiveMongoTemplate mongoTemplate;

    public Flux<Object> queryCollection(String collection ) {
        // Define the collection name
        String collectionName = collection;

        // Create a query
        Query query = new Query();

        // Execute the query
        var results = mongoTemplate.find(query, Object.class, collectionName);

        return results;

    }
}