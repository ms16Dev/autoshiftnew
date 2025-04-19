package com.example.demo.interfaces;


import com.example.demo.interfaces.dto.CreatPostCommand;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.http.ResponseEntity.created;


@RestController
@RequestMapping(value = {"/ref-data", "/ref-data/"})
@RequiredArgsConstructor
public class ReferenceController {

    private final ReactiveMongoTemplate mongoTemplate;

    @GetMapping("")
    public Mono<ResponseEntity<Flux<Object>>> getReferenceData() {
        Flux<Object> data = mongoTemplate.findAll(Object.class, "ref-data");

        // Return a ResponseEntity wrapping the Flux
        return Mono.just(ResponseEntity.ok(data));
    }

    @GetMapping("roles")
    public Mono<ResponseEntity<Flux<RoleRequest>>> getRoles() {
        Flux<RoleRequest> data = mongoTemplate.findAll(RoleRequest.class, "roles");

        // Return a ResponseEntity wrapping the Flux
        return Mono.just(ResponseEntity.ok(data));
    }

    @PostMapping("roles")
    public Mono<ResponseEntity<String>> createRole(@RequestBody RoleRequest roleRequest) {
        return mongoTemplate.save(roleRequest, "roles")
                .map(savedRole -> ResponseEntity.ok("Role created successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error creating role: " + e.getMessage())
                ));
    }

    @PutMapping("roles/{id}")
    public Mono<ResponseEntity<String>> updateRole(
            @PathVariable String id,
            @RequestBody RoleRequest roleRequest) {

        // Set the ID from path variable to ensure we're updating the correct document
        roleRequest.setId(id);

        return mongoTemplate.save(roleRequest, "roles")
                .map(savedRole -> ResponseEntity.ok("Role updated successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error updating role: " + e.getMessage())
                ));
    }


    @DeleteMapping("/roles/{id}")
    public Mono<ResponseEntity<String>> deleteRole(@PathVariable String id) {
        return mongoTemplate.remove(
                        Query.query(Criteria.where("_id").is(id)),
                        "roles"
                )
                .map(deleteResult -> {
                    if (deleteResult.getDeletedCount() > 0) {
                        return ResponseEntity.ok("Role deleted successfully");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Role not found with id: " + id);
                    }
                })
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error deleting role: " + e.getMessage())
                ));
    }

    @Setter
    @Getter
    public static class RoleRequest {
        // Getter and setter
        private String id;
        private String name;

    }

}


