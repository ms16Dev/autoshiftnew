package com.example.demo.interfaces;


import com.example.demo.domain.model.Car;
import com.example.demo.domain.model.Comment;
import com.example.demo.interfaces.dto.CommentForm;
import com.example.demo.interfaces.dto.CreatPostCommand;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;


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


    @GetMapping("makes")
    public Mono<ResponseEntity<Flux<MakeRequest>>> getMakes() {
        Flux<MakeRequest> data = mongoTemplate.findAll(MakeRequest.class, "makes");

        // Return a ResponseEntity wrapping the Flux
        return Mono.just(ResponseEntity.ok(data));
    }

    @PostMapping("makes")
    public Mono<ResponseEntity<String>> createMake(@RequestBody MakeRequest makeRequest) {
        return mongoTemplate.save(makeRequest, "makes")
                .map(savedRole -> ResponseEntity.ok("Make created successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error creating make: " + e.getMessage())
                ));
    }

    @PutMapping("makes/{id}")
    public Mono<ResponseEntity<String>> updateMake(
            @PathVariable String id,
            @RequestBody MakeRequest makeRequest) {

        // Set the ID from path variable to ensure we're updating the correct document
        makeRequest.setId(id);

        return mongoTemplate.save(makeRequest, "makes")
                .map(savedMake -> ResponseEntity.ok("Make updated successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error updating make: " + e.getMessage())
                ));
    }

    @DeleteMapping("/makes/{id}")
    public Mono<ResponseEntity<String>> deleteMake(@PathVariable String id) {
        return mongoTemplate.remove(
                        Query.query(Criteria.where("_id").is(id)),
                        "makes"
                )
                .map(deleteResult -> {
                    if (deleteResult.getDeletedCount() > 0) {
                        return ResponseEntity.ok("Make deleted successfully");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Role not found with id: " + id);
                    }
                })
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error deleting make: " + e.getMessage())
                ));
    }

    @PostMapping("/makes/{id}/classes")
    public Mono<Boolean> createClassOf(
            @PathVariable("id") String id,
            @RequestBody @Valid ClassRequest classRequest) {

        // 1. First create the class document with both names
        return mongoTemplate.insert(
                        ClassRequest.builder()
                                .name_en(classRequest.getName_en())
                                .name_ar(classRequest.getName_ar())
                                .build()
                )
                // 2. Get the ID of the newly created class
                .map(ClassRequest::getId)
                // 3. Update the Make document to reference this new class
                .flatMap(classId ->
                        mongoTemplate.update(MakeRequest.class)
                                .matching(where("id").is(id))
                                .apply(new Update().push("classes", classId))
                                .all()
                                .map(result -> result.getModifiedCount() == 1L)
                );
    }
    @GetMapping("/makes/{id}/classes")
    public Flux<ClassRequest> getClassesOf(@PathVariable("id") String makeId) {
        return mongoTemplate.findById(makeId, MakeRequest.class)
                .flatMapMany(make -> {
                    if (make.getClasses() == null || make.getClasses().isEmpty()) {
                        return Flux.empty();
                    }
                    return Flux.fromIterable(make.getClasses())
                            .flatMap(classId -> mongoTemplate.findById(classId, ClassRequest.class));
                });
    }


    @PutMapping("/classes/{id}")
    public Mono<ResponseEntity<String>> updateClass(
            @PathVariable String id,
            @RequestBody ClassRequest classRequest) {

        // Set the ID from path variable to ensure we're updating the correct document
        classRequest.setId(id);

        return mongoTemplate.save(classRequest, "classes")
                .map(savedRole -> ResponseEntity.ok("Class updated successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error updating class: " + e.getMessage())
                ));
    }


    @DeleteMapping("/classes/{id}")
    public Mono<ResponseEntity<String>> deleteClass(@PathVariable String id) {
        return mongoTemplate.remove(
                        Query.query(Criteria.where("_id").is(id)),
                        "classes"
                )
                .map(deleteResult -> {
                    if (deleteResult.getDeletedCount() > 0) {
                        return ResponseEntity.ok("Class deleted successfully");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Class not found with id: " + id);
                    }
                })
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error deleting class: " + e.getMessage())
                ));
    }

    @GetMapping("/engines")
    public Mono<ResponseEntity<Flux<Engine>>> getEngines() {
        Flux<Engine> data = mongoTemplate.findAll(Engine.class, "engines");

        // Return a ResponseEntity wrapping the Flux
        return Mono.just(ResponseEntity.ok(data));
    }
    @PostMapping("/engines")
    public Mono<ResponseEntity<String>> createEngine(@RequestBody Engine engine) {
        return mongoTemplate.save(engine, "engines")
                .map(savedEngine -> ResponseEntity.ok("Engine created successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error creating engine: " + e.getMessage())
                ));
    }

    @PutMapping("/engines/{id}")
    public Mono<ResponseEntity<String>> updateEngine(
            @PathVariable String id,
            @RequestBody Engine engine) {

        // Set the ID from path variable to ensure we're updating the correct document
        engine.setId(id);

        return mongoTemplate.save(engine, "engines")
                .map(savedEngine -> ResponseEntity.ok("Engine updated successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error updating engine: " + e.getMessage())
                ));
    }

    @DeleteMapping("/engines/{id}")
    public Mono<ResponseEntity<String>> deleteEngine(@PathVariable String id) {
        return mongoTemplate.remove(
                        Query.query(Criteria.where("_id").is(id)),
                        "engines"
                )
                .map(deleteResult -> {
                    if (deleteResult.getDeletedCount() > 0) {
                        return ResponseEntity.ok("Engine deleted successfully");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Engine not found with id: " + id);
                    }
                })
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error deleting role: " + e.getMessage())
                ));
    }

    @GetMapping("/fuel")
    public Mono<ResponseEntity<Flux<Fuel>>> getFuels() {
        Flux<Fuel> data = mongoTemplate.findAll(Fuel.class, "fuels");

        // Return a ResponseEntity wrapping the Flux
        return Mono.just(ResponseEntity.ok(data));
    }
    @PostMapping("/fuel")
    public Mono<ResponseEntity<String>> createFuel(@RequestBody Fuel fuel) {
        return mongoTemplate.save(fuel, "fuels")
                .map(savedEngine -> ResponseEntity.ok("Fuel created successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error creating fuel: " + e.getMessage())
                ));
    }

    @PutMapping("/fuel/{id}")
    public Mono<ResponseEntity<String>> updateFuel(
            @PathVariable String id,
            @RequestBody Fuel fuel) {

        // Set the ID from path variable to ensure we're updating the correct document
        fuel.setId(id);

        return mongoTemplate.save(fuel, "fuels")
                .map(savedEngine -> ResponseEntity.ok("Fuel updated successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error updating fuel: " + e.getMessage())
                ));
    }

    @DeleteMapping("/fuel/{id}")
    public Mono<ResponseEntity<String>> deleteFuel(@PathVariable String id) {
        return mongoTemplate.remove(
                        Query.query(Criteria.where("_id").is(id)),
                        "fuels"
                )
                .map(deleteResult -> {
                    if (deleteResult.getDeletedCount() > 0) {
                        return ResponseEntity.ok("Fuel deleted successfully");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Fuel not found with id: " + id);
                    }
                })
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error deleting fuel: " + e.getMessage())
                ));
    }


    @GetMapping("/gears")
    public Mono<ResponseEntity<Flux<Gear>>> getGears() {
        Flux<Gear> data = mongoTemplate.findAll(Gear.class, "gears");

        // Return a ResponseEntity wrapping the Flux
        return Mono.just(ResponseEntity.ok(data));
    }
    @PostMapping("/gears")
    public Mono<ResponseEntity<String>> createGear(@RequestBody Gear gear) {
        return mongoTemplate.save(gear, "gears")
                .map(savedEngine -> ResponseEntity.ok("Gear created successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error creating gear: " + e.getMessage())
                ));
    }

    @PutMapping("/gears/{id}")
    public Mono<ResponseEntity<String>> updateGear(
            @PathVariable String id,
            @RequestBody Gear gear) {

        // Set the ID from path variable to ensure we're updating the correct document
        gear.setId(id);

        return mongoTemplate.save(gear, "gears")
                .map(savedEngine -> ResponseEntity.ok("Gear updated successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error updating gear: " + e.getMessage())
                ));
    }

    @DeleteMapping("/gears/{id}")
    public Mono<ResponseEntity<String>> deleteGear(@PathVariable String id) {
        return mongoTemplate.remove(
                        Query.query(Criteria.where("_id").is(id)),
                        "gears"
                )
                .map(deleteResult -> {
                    if (deleteResult.getDeletedCount() > 0) {
                        return ResponseEntity.ok("Gear deleted successfully");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Gear not found with id: " + id);
                    }
                })
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error deleting gear: " + e.getMessage())
                ));
    }

    @GetMapping("/colors")
    public Mono<ResponseEntity<Flux<Color>>> getColors() {
        Flux<Color> data = mongoTemplate.findAll(Color.class, "colors");

        // Return a ResponseEntity wrapping the Flux
        return Mono.just(ResponseEntity.ok(data));
    }
    @PostMapping("/colors")
    public Mono<ResponseEntity<String>> createColor(@RequestBody Color color) {
        return mongoTemplate.save(color, "colors")
                .map(savedEngine -> ResponseEntity.ok("Color created successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error creating color: " + e.getMessage())
                ));
    }

    @PutMapping("/colors/{id}")
    public Mono<ResponseEntity<String>> updateColor(
            @PathVariable String id,
            @RequestBody Color color) {

        // Set the ID from path variable to ensure we're updating the correct document
        color.setId(id);

        return mongoTemplate.save(color, "colors")
                .map(savedEngine -> ResponseEntity.ok("Color updated successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error updating color: " + e.getMessage())
                ));
    }

    @DeleteMapping("/colors/{id}")
    public Mono<ResponseEntity<String>> deleteColor(@PathVariable String id) {
        return mongoTemplate.remove(
                        Query.query(Criteria.where("_id").is(id)),
                        "colors"
                )
                .map(deleteResult -> {
                    if (deleteResult.getDeletedCount() > 0) {
                        return ResponseEntity.ok("Color deleted successfully");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Color not found with id: " + id);
                    }
                })
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error deleting color: " + e.getMessage())
                ));
    }

 @GetMapping("/shapes")
    public Mono<ResponseEntity<Flux<Shape>>> getShapes() {
        Flux<Shape> data = mongoTemplate.findAll(Shape.class, "shapes");

        // Return a ResponseEntity wrapping the Flux
        return Mono.just(ResponseEntity.ok(data));
    }
    @PostMapping("/shapes")
    public Mono<ResponseEntity<String>> createShape(@RequestBody Shape shape) {
        return mongoTemplate.save(shape, "shapes")
                .map(savedEngine -> ResponseEntity.ok("Shape created successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error creating shape: " + e.getMessage())
                ));
    }

    @PutMapping("/shapes/{id}")
    public Mono<ResponseEntity<String>> updateShape(
            @PathVariable String id,
            @RequestBody Shape shape) {

        // Set the ID from path variable to ensure we're updating the correct document
        shape.setId(id);

        return mongoTemplate.save(shape, "shapes")
                .map(savedEngine -> ResponseEntity.ok("Shape updated successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error updating shape: " + e.getMessage())
                ));
    }

    @DeleteMapping("/shapes/{id}")
    public Mono<ResponseEntity<String>> deleteShape(@PathVariable String id) {
        return mongoTemplate.remove(
                        Query.query(Criteria.where("_id").is(id)),
                        "shapes"
                )
                .map(deleteResult -> {
                    if (deleteResult.getDeletedCount() > 0) {
                        return ResponseEntity.ok("Shape deleted successfully");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Shape not found with id: " + id);
                    }
                })
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error deleting shape: " + e.getMessage())
                ));
    }



 @GetMapping("/luxury")
    public Mono<ResponseEntity<Flux<Luxury>>> getLuxury() {
        Flux<Luxury> data = mongoTemplate.findAll(Luxury.class, "luxury");

        // Return a ResponseEntity wrapping the Flux
        return Mono.just(ResponseEntity.ok(data));
    }
    @PostMapping("/luxury")
    public Mono<ResponseEntity<String>> createLuxury(@RequestBody Luxury luxury) {
        return mongoTemplate.save(luxury, "luxury")
                .map(savedEngine -> ResponseEntity.ok("Luxury created successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error creating luxury: " + e.getMessage())
                ));
    }

    @PutMapping("/luxury/{id}")
    public Mono<ResponseEntity<String>> updateLuxury(
            @PathVariable String id,
            @RequestBody Luxury luxury) {

        // Set the ID from path variable to ensure we're updating the correct document
        luxury.setId(id);

        return mongoTemplate.save(luxury, "luxury")
                .map(savedEngine -> ResponseEntity.ok("Luxury updated successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error updating luxury: " + e.getMessage())
                ));
    }

    @DeleteMapping("/luxury/{id}")
    public Mono<ResponseEntity<String>> deleteLuxury(@PathVariable String id) {
        return mongoTemplate.remove(
                        Query.query(Criteria.where("_id").is(id)),
                        "luxury"
                )
                .map(deleteResult -> {
                    if (deleteResult.getDeletedCount() > 0) {
                        return ResponseEntity.ok("Luxury deleted successfully");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Luxury not found with id: " + id);
                    }
                })
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error deleting luxury: " + e.getMessage())
                ));
    }


 @GetMapping("/safety")
    public Mono<ResponseEntity<Flux<Safety>>> getSafety() {
        Flux<Safety> data = mongoTemplate.findAll(Safety.class, "safety");

        // Return a ResponseEntity wrapping the Flux
        return Mono.just(ResponseEntity.ok(data));
    }
    @PostMapping("/safety")
    public Mono<ResponseEntity<String>> createSafety(@RequestBody Safety safety) {
        return mongoTemplate.save(safety, "safety")
                .map(savedEngine -> ResponseEntity.ok("Safety created successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error creating safety: " + e.getMessage())
                ));
    }

    @PutMapping("/safety/{id}")
    public Mono<ResponseEntity<String>> updateSafety(
            @PathVariable String id,
            @RequestBody Safety safety) {

        // Set the ID from path variable to ensure we're updating the correct document
        safety.setId(id);

        return mongoTemplate.save(safety, "safety")
                .map(savedEngine -> ResponseEntity.ok("Safety updated successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error updating safety: " + e.getMessage())
                ));
    }

    @DeleteMapping("/safety/{id}")
    public Mono<ResponseEntity<String>> deleteSafety(@PathVariable String id) {
        return mongoTemplate.remove(
                        Query.query(Criteria.where("_id").is(id)),
                        "safety"
                )
                .map(deleteResult -> {
                    if (deleteResult.getDeletedCount() > 0) {
                        return ResponseEntity.ok("Safety deleted successfully");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Safety not found with id: " + id);
                    }
                })
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error deleting safety: " + e.getMessage())
                ));
    }



    @Setter
    @Getter
    public static class RoleRequest {
        // Getter and setter
        private String id;
        private String name;

    }

    @Setter
    @Getter
    @Document(collection = "makes")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MakeRequest implements Serializable {
        // Getter and setter
        private String id;
        private String name_en;
        private String name_ar;
        private String url;
        List<String> classes = Collections.emptyList();


    }
    @Setter
    @Getter
    @Document(collection = "classes")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClassRequest  implements Serializable {
        // Getter and setter
        private String id;
        private String name_en;
        private String name_ar;
    }

    @Setter
    @Getter
    public static class Engine {
        // Getter and setter
        private String id;
        private String name_en;
        private String name_ar;
    }

    @Setter
    @Getter
    public static class Fuel {
        // Getter and setter
        private String id;
        private String name_en;
        private String name_ar;
    }

    @Setter
    @Getter
    public static class Gear {
        // Getter and setter
        private String id;
        private String name_en;
        private String name_ar;
    }


    @Setter
    @Getter
    public static class Color {
        // Getter and setter
        private String id;
        private String name_en;
        private String name_ar;
    }


    @Setter
    @Getter
    public static class Shape {
        // Getter and setter
        private String id;
        private String name_en;
        private String name_ar;
    }

    @Setter
    @Getter
    public static class Luxury {
        // Getter and setter
        private String id;
        private String name_en;
        private String name_ar;
    }

    @Setter
    @Getter
    public static class Safety {
        // Getter and setter
        private String id;
        private String name_en;
        private String name_ar;
    }


}


