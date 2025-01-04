package com.example.demo.interfaces;


import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import reactor.core.publisher.Flux;
@RestController
@RequestMapping(value = {"/ref-data", "/ref-data/"})
@RequiredArgsConstructor
public class ReferenceController {

    private final ReactiveMongoTemplate mongoTemplate;

    @GetMapping
    public Mono<ResponseEntity<Object>> getReferenceData() {
        // Query the "ref-data" collection to fetch a single object
        Query query = new Query(); // You can add criteria if necessary
        return mongoTemplate.findOne(query, Object.class, "ref-data")
                .map(data -> ResponseEntity.ok(data))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}


