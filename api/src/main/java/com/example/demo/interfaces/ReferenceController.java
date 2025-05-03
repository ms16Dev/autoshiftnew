package com.example.demo.interfaces;


import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.annotation.Id;
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
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;


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
    public Mono<ResponseEntity<Flux<Role>>> getRoles() {
        Flux<Role> data = mongoTemplate.findAll(Role.class, "roles");

        // Return a ResponseEntity wrapping the Flux
        return Mono.just(ResponseEntity.ok(data));
    }

    @PostMapping("roles")
    public Mono<ResponseEntity<String>> createRole(@RequestBody Role role) {
        // First, check if a role with the same ID already exists
        return mongoTemplate.findOne(Query.query(Criteria.where("id").is(role.getId())), Role.class)
                .flatMap(existingRole -> {
                    // If a role with the same ID exists, return a 409 Conflict response
                    return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body("Role with the same ID already exists."));
                })
                // Proceed with saving the role if no duplicate is found
                .switchIfEmpty(
                        mongoTemplate.save(role, "roles")
                                .map(savedRole -> ResponseEntity.ok("Role created successfully"))
                                .onErrorResume(e -> Mono.just(
                                        ResponseEntity.internalServerError().body("Error creating role: " + e.getMessage())
                                ))
                );
    }


    @PutMapping("roles/{id}")
    public Mono<ResponseEntity<String>> updateRole(
            @PathVariable String id,
            @RequestBody Role role) {

        // Set the ID from path variable to ensure we're updating the correct document
        role.setId(id);

        return mongoTemplate.save(role, "roles")
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
    public Mono<ResponseEntity<Flux<Make>>> getMakes() {
        Flux<Make> data = mongoTemplate.findAll(Make.class, "makes");

        // Return a ResponseEntity wrapping the Flux
        return Mono.just(ResponseEntity.ok(data));
    }

    @PostMapping("makes")
    public Mono<ResponseEntity<String>> createMake(@RequestBody Make make) {
        return mongoTemplate.save(make, "makes")
                .map(savedRole -> ResponseEntity.ok("Make created successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error creating make: " + e.getMessage())
                ));
    }

    @PutMapping("makes/{id}")
    public Mono<ResponseEntity<String>> updateMake(
            @PathVariable String id,
            @RequestBody Make make) {

        // Set the ID from path variable to ensure we're updating the correct document
        make.setId(id);

        return mongoTemplate.save(make, "makes")
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
    public Mono<ResponseEntity<String>> createClassOf(
            @PathVariable("id") String id,
            @RequestBody @Valid CarClass carClass) {

        // First check if the class already exists by its ID
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(carClass.getId())), CarClass.class)
                .flatMap(existingClass -> {
                    // If the class exists, return a 409 Conflict with a message
                    return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body("Class ID already exists."));
                })
                // Proceed to insert the new class if it doesn't exist
                .switchIfEmpty(
                        mongoTemplate.insert(
                                        CarClass.builder()
                                                .id(carClass.getId())
                                                .name_en(carClass.getName_en())
                                                .name_ar(carClass.getName_ar())
                                                .build()
                                )
                                .map(CarClass::getId)
                                .flatMap(classId ->
                                        mongoTemplate.update(Make.class)
                                                .matching(where("id").is(id))
                                                .apply(new Update().push("classes", classId))
                                                .all()
                                                .map(result -> result.getModifiedCount() == 1L)
                                )
                                // Success response with message
                                .map(success -> {
                                    if (success) {
                                        return ResponseEntity.ok("Class successfully added.");
                                    }
                                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add class.");
                                })
                );
    }


    @GetMapping("/makes/{id}/classes")
    public Flux<CarClass> getClassesOf(@PathVariable("id") String makeId) {
        return mongoTemplate.findById(makeId, Make.class)
                .flatMapMany(make -> {
                    if (make.getClasses() == null || make.getClasses().isEmpty()) {
                        return Flux.empty();
                    }
                    return Flux.fromIterable(make.getClasses())
                            .flatMap(classId -> mongoTemplate.findById(classId, CarClass.class));
                });
    }


    @PutMapping("/classes/{id}")
    public Mono<ResponseEntity<String>> updateClass(
            @PathVariable String id,
            @RequestBody CarClass carClass) {

        // Set the ID from path variable to ensure we're updating the correct document
        carClass.setId(id);

        return mongoTemplate.save(carClass, "classes")
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

        // First check if the engine already exists by its ID
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(engine.getId())), Engine.class)
                .flatMap(existingEngine -> {
                    // If the engine exists, return a 409 Conflict with a message
                    return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body("Engine ID already exists."));
                })
                // Proceed to insert the new engine if it doesn't exist
                .switchIfEmpty(
                        mongoTemplate.insert(
                                        Engine.builder()
                                                .id(engine.getId())
                                                .name_en(engine.getName_en())
                                                .name_ar(engine.getName_ar())
                                                .build()
                                )
                                .map(Engine::getId)
                                // Success response with message
                                .map(engineId -> ResponseEntity.ok("Engine successfully created."))
                                .onErrorResume(e -> Mono.just(
                                        ResponseEntity.internalServerError().body("Error creating engine: " + e.getMessage()))
                                )
                );
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
        Flux<Fuel> data = mongoTemplate.findAll(Fuel.class, "fuel");

        // Return a ResponseEntity wrapping the Flux
        return Mono.just(ResponseEntity.ok(data));
    }

    @PostMapping("/fuel")
    public Mono<ResponseEntity<String>> createFuel(@RequestBody Fuel fuel) {
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(fuel.getId())), Fuel.class)
                .flatMap(existingFuel -> {
                    // If fuel exists, respond cleanly
                    return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body("Fuel ID already exists."));
                })
                .switchIfEmpty(
                        mongoTemplate.insert(
                                        Fuel.builder()
                                                .id(fuel.getId())
                                                .name_en(fuel.getName_en())
                                                .name_ar(fuel.getName_ar())
                                                .build()
                                )
                                .thenReturn(ResponseEntity.ok("Fuel created successfully."))
                                .onErrorResume(e -> Mono.just(ResponseEntity
                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body("Failed to create fuel.")))
                );
    }




    @PutMapping("/fuel/{id}")
    public Mono<ResponseEntity<String>> updateFuel(
            @PathVariable String id,
            @RequestBody Fuel fuel) {

        // Set the ID from path variable to ensure we're updating the correct document
        fuel.setId(id);

        return mongoTemplate.save(fuel, "fuel")
                .map(savedEngine -> ResponseEntity.ok("Fuel updated successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error updating fuel: " + e.getMessage())
                ));
    }

    @DeleteMapping("/fuel/{id}")
    public Mono<ResponseEntity<String>> deleteFuel(@PathVariable String id) {
        return mongoTemplate.remove(
                        Query.query(Criteria.where("_id").is(id)),
                        "fuel"
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

        // First check if the gear already exists by its ID
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(gear.getId())), Gear.class)
                .flatMap(existingGear -> {
                    // If the gear exists, return a 409 Conflict with a message
                    return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body("Gear ID already exists."));
                })
                // Proceed to insert the new gear if it doesn't exist
                .switchIfEmpty(
                        mongoTemplate.insert(
                                        Gear.builder()
                                                .id(gear.getId())
                                                .name_en(gear.getName_en())
                                                .name_ar(gear.getName_ar())
                                                .build()
                                )
                                .map(Gear::getId)
                                // Success response with message
                                .map(gearId -> ResponseEntity.ok("Gear successfully created."))
                                .onErrorResume(e -> Mono.just(
                                        ResponseEntity.internalServerError().body("Error creating gear: " + e.getMessage()))
                                )
                );
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

        // First check if the color already exists by its ID
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(color.getId())), Color.class)
                .flatMap(existingColor -> {
                    // If the color exists, return a 409 Conflict with a message
                    return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body("Color ID already exists."));
                })
                // Proceed to insert the new color if it doesn't exist
                .switchIfEmpty(
                        mongoTemplate.insert(
                                        Color.builder()
                                                .id(color.getId())
                                                .name_en(color.getName_en())
                                                .name_ar(color.getName_ar())
                                                .build()
                                )
                                .map(Color::getId)
                                // Success response with message
                                .map(colorId -> ResponseEntity.ok("Color successfully created."))
                                .onErrorResume(e -> Mono.just(
                                        ResponseEntity.internalServerError().body("Error creating color: " + e.getMessage()))
                                )
                );
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

        // First check if the shape already exists by its ID
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(shape.getId())), Shape.class)
                .flatMap(existingShape -> {
                    // If the shape exists, return a 409 Conflict with a message
                    return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body("Shape ID already exists."));
                })
                // Proceed to insert the new shape if it doesn't exist
                .switchIfEmpty(
                        mongoTemplate.insert(
                                        Shape.builder()
                                                .id(shape.getId())
                                                .name_en(shape.getName_en())
                                                .name_ar(shape.getName_ar())
                                                .build()
                                )
                                .map(Shape::getId)
                                // Success response with message
                                .map(shapeId -> ResponseEntity.ok("Shape successfully created."))
                                .onErrorResume(e -> Mono.just(
                                        ResponseEntity.internalServerError().body("Error creating shape: " + e.getMessage()))
                                )
                );
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

        // First check if the luxury item already exists by its ID
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(luxury.getId())), Luxury.class)
                .flatMap(existingLuxury -> {
                    // If it exists, return a 409 Conflict
                    return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body("Luxury ID already exists."));
                })
                // If not found, insert it
                .switchIfEmpty(
                        mongoTemplate.insert(
                                        Luxury.builder()
                                                .id(luxury.getId())
                                                .name_en(luxury.getName_en())
                                                .name_ar(luxury.getName_ar())
                                                .build()
                                )
                                .map(Luxury::getId)
                                .map(luxuryId -> ResponseEntity.ok("Luxury successfully created."))
                                .onErrorResume(e -> Mono.just(
                                        ResponseEntity.internalServerError().body("Error creating luxury: " + e.getMessage()))
                                )
                );
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

        // First check if a Safety item with the same ID already exists
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(safety.getId())), Safety.class)
                .flatMap(existingSafety ->
                        Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body("Safety ID already exists."))
                )
                // If not found, insert it
                .switchIfEmpty(
                        mongoTemplate.insert(
                                        Safety.builder()
                                                .id(safety.getId())
                                                .name_en(safety.getName_en())
                                                .name_ar(safety.getName_ar())
                                                .build()
                                )
                                .map(Safety::getId)
                                .map(savedId -> ResponseEntity.ok("Safety successfully created."))
                                .onErrorResume(e -> Mono.just(
                                        ResponseEntity.internalServerError().body("Error creating safety: " + e.getMessage()))
                                )
                );
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


    @GetMapping("/countries")
    public Mono<ResponseEntity<Flux<Country>>> getCountries() {
        Flux<Country> data = mongoTemplate.findAll(Country.class, "countries");
        return Mono.just(ResponseEntity.ok(data));
    }

    @PostMapping("/countries")
    public Mono<ResponseEntity<String>> createCountry(@RequestBody @Valid Country country) {

        // First check if a Country item with the same ID already exists
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(country.getId())), Country.class)
                .flatMap(existingCountry ->
                        Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body("Country ID already exists."))
                )
                // If not found, insert it
                .switchIfEmpty(
                        mongoTemplate.insert(
                                        Country.builder()
                                                .id(country.getId())
                                                .name_en(country.getName_en())
                                                .name_ar(country.getName_ar())
                                                .build()
                                )
                                .map(Country::getId)
                                .map(savedId -> ResponseEntity.ok("Country created successfully."))
                                .onErrorResume(e -> Mono.just(
                                        ResponseEntity.internalServerError().body("Error creating country: " + e.getMessage()))
                                )
                );
    }


    @PutMapping("/countries/{id}")
    public Mono<ResponseEntity<String>> updateCountry(@PathVariable String id, @RequestBody Country country) {
        country.setId(id);
        return mongoTemplate.save(country, "countries")
                .map(saved -> ResponseEntity.ok("Country updated successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error: " + e.getMessage()))
                );
    }

    @DeleteMapping("/countries/{id}")
    public Mono<ResponseEntity<String>> deleteCountry(@PathVariable String id) {
        return mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), "countries")
                .flatMap(result -> {
                    if (result.getDeletedCount() > 0) {
                        return Mono.just(ResponseEntity.ok("Country deleted successfully"));
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Country not found with id: " + id));
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body("Error: " + e.getMessage())));
    }


    @PostMapping("/countries/{id}/cities")
    public Mono<ResponseEntity<String>> createCityOf(@PathVariable String id, @RequestBody @Valid City city) {
        city.setCountryId(id); // denormalized ref

        return mongoTemplate.insert(city, "cities")
                .flatMap(savedCity ->
                        mongoTemplate.update(Country.class)
                                .matching(Query.query(Criteria.where("_id").is(id)))
                                .apply(new Update().push("cities", savedCity.getId()))
                                .all()
                                .flatMap(result -> {
                                    if (result.getModifiedCount() == 1L) {
                                        return Mono.just(ResponseEntity.ok("City created and linked."));
                                    }
                                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                            .body("Country not found."));
                                }))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error: " + e.getMessage()))
                );
    }

    @GetMapping("/countries/{id}/cities")
    public Flux<City> getCitiesOf(@PathVariable String id) {
        return mongoTemplate.findById(id, Country.class)
                .flatMapMany(country -> {
                    if (country.getCities() == null || country.getCities().isEmpty()) {
                        return Flux.empty();
                    }
                    return Flux.fromIterable(country.getCities())
                            .flatMap(cityId -> mongoTemplate.findById(cityId, City.class));
                });
    }

    @GetMapping("/cities")
    public Mono<ResponseEntity<Flux<City>>> getCities() {
        Flux<City> data = mongoTemplate.findAll(City.class, "cities");
        return Mono.just(ResponseEntity.ok(data));
    }

    @PutMapping("/cities/{id}")
    public Mono<ResponseEntity<String>> updateCity(@PathVariable String id, @RequestBody City city) {
        city.setId(id);
        return mongoTemplate.save(city, "cities")
                .map(saved -> ResponseEntity.ok("City updated successfully"))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body("Error: " + e.getMessage())));
    }

    @DeleteMapping("/cities/{id}")
    public Mono<ResponseEntity<String>> deleteCity(@PathVariable String id) {
        return mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), "cities")
                .flatMap(result -> {
                    if (result.getDeletedCount() > 0) {
                        return mongoTemplate.updateMulti(
                                        Query.query(Criteria.where("cities").in(id)),
                                        new Update().pull("cities", id),
                                        Country.class)
                                .thenReturn(ResponseEntity.ok("City deleted and references removed."));
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("City not found."));
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body("Error: " + e.getMessage())));
    }


    @PostMapping("/countries/{id}/currencies")
    public Mono<ResponseEntity<String>> createCurrencyOf(@PathVariable String id, @RequestBody @Valid Currency currency) {
        currency.setCountryId(id); // denormalized ref

        // First check if currency with the same ID already exists
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(currency.getId())), Currency.class)
                .flatMap(existingCurrency ->
                        Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body("Currency ID already exists."))
                )
                // If not found, proceed with insertion and linking
                .switchIfEmpty(
                        mongoTemplate.insert(currency, "currencies")
                                .flatMap(savedCurrency ->
                                        mongoTemplate.update(Country.class)
                                                .matching(Query.query(Criteria.where("_id").is(id)))
                                                .apply(new Update().push("currencies", savedCurrency.getId()))
                                                .all()
                                                .flatMap(result -> {
                                                    if (result.getModifiedCount() == 1L) {
                                                        return Mono.just(ResponseEntity.ok("Currency created and linked."));
                                                    }
                                                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                            .body("Country not found."));
                                                }))
                )
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error: " + e.getMessage()))
                );
    }

    @GetMapping("/countries/{id}/currencies")
    public Flux<Currency> getCurrenciesOf(@PathVariable String id) {
        return mongoTemplate.findById(id, Country.class)
                .doOnNext(country -> System.out.println("Fetched Country: " + country))
                .flatMapMany(country -> {
                    if (country.getCurrencies() == null || country.getCurrencies().isEmpty()) {
                        return Flux.empty();
                    }
                    return Flux.fromIterable(country.getCurrencies())
                            .flatMap(currencyId -> mongoTemplate.findById(currencyId, Currency.class));
                });
    }

    @GetMapping("/currencies")
    public Mono<ResponseEntity<Flux<Currency>>> getCurrencies() {
        Flux<Currency> data = mongoTemplate.findAll(Currency.class, "currencies");
        return Mono.just(ResponseEntity.ok(data));
    }


    @PutMapping("/currencies/{id}")
    public Mono<ResponseEntity<String>> updateCurrency(@PathVariable String id, @RequestBody Currency currency) {
        currency.setId(id);
        return mongoTemplate.save(currency, "currencies")
                .map(saved -> ResponseEntity.ok("Currency updated successfully"))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body("Error: " + e.getMessage())));
    }

    @DeleteMapping("/currencies/{id}")
    public Mono<ResponseEntity<String>> deleteCurrency(@PathVariable String id) {
        return mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), "currencies")
                .flatMap(result -> {
                    if (result.getDeletedCount() > 0) {
                        return mongoTemplate.updateMulti(
                                        Query.query(Criteria.where("currencies").in(id)),
                                        new Update().pull("currencies", id),
                                        Country.class)
                                .thenReturn(ResponseEntity.ok("Currency deleted and references removed."));
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Currency not found."));
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body("Error: " + e.getMessage())));
    }


    @GetMapping("/status")
    public Mono<ResponseEntity<Flux<Status>>> getStatus() {
        Flux<Status> data = mongoTemplate.findAll(Status.class, "status");

        // Return a ResponseEntity wrapping the Flux
        return Mono.just(ResponseEntity.ok(data));
    }

    @PostMapping("/status")
    public Mono<ResponseEntity<String>> createStatus(@RequestBody Status status) {
        return mongoTemplate.save(status, "status")
                .map(savedEngine -> ResponseEntity.ok("Status created successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error creating status: " + e.getMessage())
                ));
    }

    @PutMapping("/status/{id}")
    public Mono<ResponseEntity<String>> updateStatus(
            @PathVariable String id,
            @RequestBody Status status) {

        // Set the ID from path variable to ensure we're updating the correct document
        status.setId(id);

        return mongoTemplate.save(status, "status")
                .map(savedEngine -> ResponseEntity.ok("Status updated successfully"))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error updating status: " + e.getMessage())
                ));
    }

    @DeleteMapping("/status/{id}")
    public Mono<ResponseEntity<String>> deleteStatus(@PathVariable String id) {
        return mongoTemplate.remove(
                        Query.query(Criteria.where("_id").is(id)),
                        "status"
                )
                .map(deleteResult -> {
                    if (deleteResult.getDeletedCount() > 0) {
                        return ResponseEntity.ok("Status deleted successfully");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Status not found with id: " + id);
                    }
                })
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().body("Error deleting status: " + e.getMessage())
                ));
    }


    @Setter
    @Getter
    @Document(collection = "roles")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Role {
        // Getter and setter
        @Id
        private String id;
        private String name_en;
        private String name_ar;

    }

    @Setter
    @Getter
    @Document(collection = "makes")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Make implements Serializable {
        // Getter and setter
        @Id
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
    public static class CarClass implements Serializable {
        // Getter and setter
        @Id
        private String id;
        private String name_en;
        private String name_ar;
    }

    @Setter
    @Getter
    @Document(collection = "engines")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Engine {
        // Getter and setter
        @Id
        private String id;
        private String name_en;
        private String name_ar;
    }

    @Setter
    @Getter
    @Document(collection = "fuel")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fuel {
        // Getter and setter
        @Id
        private String id;
        private String name_en;
        private String name_ar;
    }


    @Setter
    @Getter
    @Document(collection = "gears")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Gear {
        // Getter and setter
        @Id
        private String id;
        private String name_en;
        private String name_ar;
    }


    @Setter
    @Getter
    @Document(collection = "colors")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Color {
        // Getter and setter
        @Id
        private String id;
        private String name_en;
        private String name_ar;
    }


    @Setter
    @Getter
    @Document(collection = "shapes")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Shape {
        // Getter and setter
        @Id
        private String id;
        private String name_en;
        private String name_ar;
    }

    @Setter
    @Getter
    @Document(collection = "luxury")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Luxury {
        // Getter and setter
        @Id
        private String id;
        private String name_en;
        private String name_ar;
    }

    @Setter
    @Getter
    @Document(collection = "safety")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Safety {
        // Getter and setter
        @Id
        private String id;
        private String name_en;
        private String name_ar;
    }

    @Setter
    @Getter
    @Document(collection = "countries")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Country {
        @Id
        private String id;

        private String name_en;
        private String name_ar;

        private List<String> cities;
        private List<String> currencies;
    }


    @Setter
    @Getter
    @Document(collection = "cities")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class City {
        @Id
        private String id;

        private String name_en;
        private String name_ar;

        private String countryId;
    }


    @Setter
    @Getter
    @Document(collection = "currencies")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Currency {
        // Getter and setter
        @Id
        private String id;
        private String name_en;
        private String name_ar;

        private String countryId;

    }


    @Setter
    @Getter
    @Document(collection = "status")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Status {
        // Getter and setter
        @Id
        private String id;
        private String name_en;
        private String name_ar;
    }


}


