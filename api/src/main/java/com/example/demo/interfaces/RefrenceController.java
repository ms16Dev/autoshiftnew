package com.example.demo.interfaces;


import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ref-data")
@RequiredArgsConstructor
public class RefrenceController {

    private final ReactiveMongoTemplate mongoTemplate;

    @GetMapping("/makes")
    public ResponseEntity<Flux<Object>> getCountries() {
        // Query the "countries" collection
        Query query = new Query(); // You can add criteria to filter data if needed
        Flux<Object> countries = mongoTemplate.find(query, Object.class, "makes");
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/languages")
    public ResponseEntity<Flux<Object>> getLanguages() {
        // Query the "languages" collection
        Query query = new Query(); // You can add criteria to filter data if needed
        Flux<Object> languages = mongoTemplate.find(query, Object.class, "languages");
        return ResponseEntity.ok(languages);
    }
}

