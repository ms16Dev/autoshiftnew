package com.example.demo.interfaces;


import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value = {"/ref-data", "/ref-data/"})
@RequiredArgsConstructor
public class ReferenceController {

    private final ReactiveMongoTemplate mongoTemplate;

    @GetMapping
    public Mono<ResponseEntity<Flux<Object>>> getReferenceData() {
        Flux<Object> data = mongoTemplate.findAll(Object.class, "ref-data");

        // Return a ResponseEntity wrapping the Flux
        return Mono.just(ResponseEntity.ok(data));
    }

}


