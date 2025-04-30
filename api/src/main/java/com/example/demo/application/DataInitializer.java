/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.application;

import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.interfaces.ReferenceController;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author hantsy
 */
@Component
@Slf4j
@RequiredArgsConstructor
class DataInitializer {

    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;
    private ReactiveMongoTemplate mongoTemplate;
    private final ObjectMapper objectMapper;


    @Autowired
    public DataInitializer(UserRepository users, PasswordEncoder passwordEncoder, ReactiveMongoTemplate mongoTemplate, ObjectMapper objectMapper) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.mongoTemplate = mongoTemplate;
        this.objectMapper = objectMapper;
    }


    @EventListener(value = ApplicationReadyEvent.class)
    public void init() {
        initUsers();
        initRoles();
        initStatuses();
        initMakes();
        initClasses();
        initFuel();
        initEngines();
        initGears();
        initColors();
        initShapes();
        initLuxury();
        initSafety();
        initCountries();
        initCities();
        initCurrencies();
    }

    private void initUsers() {
        log.info("start users initialization  ...");
        this.users
            .deleteAll()
            .thenMany(
                Flux
                    .just("user", "admin")
                    .flatMap(
                        username -> {
                            List<String> roles = "user".equals(username)
                                ? List.of("ROLE_USER")
                                : Arrays.asList("ROLE_USER", "ROLE_ADMIN");

                            User user = User.builder()
                                .roles(roles)
                                .username(username)
                                .password(passwordEncoder.encode("password"))
                                .email(username + "@example.com")
                                .build();
                            return this.users.create(user);
                        }
                    )
            )
            .log()
            .subscribe(
                null,
                null,
                () -> log.info("done users initialization...")
            );
    }





    private void initRoles() {
        log.info("Loading roles from JSON...");

        try {
            InputStream inputStream = getClass().getResourceAsStream("/types/roles.json");
            List<ReferenceController.Role> defaultRoles = Arrays.asList(
                    objectMapper.readValue(inputStream, ReferenceController.Role[].class)
            );

            mongoTemplate
                    .remove(new Query(), ReferenceController.Role.class)
                    .thenMany(Flux.fromIterable(defaultRoles))
                    .flatMap(mongoTemplate::save)
                    .subscribe(
                            null,
                            error -> log.error("Error initializing roles", error),
                            () -> log.info("Done roles initialization from JSON.")
                    );

        } catch (IOException e) {
            log.error("Failed to read roles.json", e);
        }
    }

    private void initStatuses() {
        log.info("Initializing statuses from JSON...");

        try {
            InputStream inputStream = getClass().getResourceAsStream("/types/status.json");

            List<ReferenceController.Status> statuses = Arrays.asList(
                    objectMapper.readValue(inputStream, ReferenceController.Status[].class)
            );

            mongoTemplate
                    .remove(new Query(), ReferenceController.Status.class)
                    .thenMany(Flux.fromIterable(statuses))
                    .flatMap(mongoTemplate::save)
                    .subscribe(
                            null,
                            error -> log.error("Error initializing statuses", error),
                            () -> log.info("Done statuses initialization.")
                    );

        } catch (IOException e) {
            log.error("Failed to read /types/status.json", e);
        }
    }


    private void initMakes() {
        log.info("Initializing makes from JSON...");

        try {
            InputStream inputStream = getClass().getResourceAsStream("/types/makes.json");

            List<ReferenceController.Make> makes = Arrays.asList(
                    objectMapper.readValue(inputStream, ReferenceController.Make[].class)
            );

            mongoTemplate
                    .remove(new Query(), ReferenceController.Make.class)
                    .thenMany(Flux.fromIterable(makes))
                    .flatMap(mongoTemplate::save)
                    .subscribe(
                            null,
                            error -> log.error("Error initializing makes", error),
                            () -> log.info("Done makes initialization.")
                    );

        } catch (IOException e) {
            log.error("Failed to read /types/makes.json", e);
        }
    }

    private void initClasses() {
        log.info("Initializing car classes from JSON...");

        try {
            InputStream inputStream = getClass().getResourceAsStream("/types/classes.json");

            List<ReferenceController.CarClass> classes = Arrays.asList(
                    objectMapper.readValue(inputStream, ReferenceController.CarClass[].class)
            );

            mongoTemplate
                    .remove(new Query(), ReferenceController.CarClass.class)
                    .thenMany(Flux.fromIterable(classes))
                    .flatMap(mongoTemplate::save)
                    .subscribe(
                            null,
                            error -> log.error("Error initializing car classes", error),
                            () -> log.info("Done car classes initialization.")
                    );

        } catch (IOException e) {
            log.error("Failed to read /types/classes.json", e);
        }
    }

    private void initFuel() {
        log.info("Initializing fuel types from JSON...");

        try {
            InputStream inputStream = getClass().getResourceAsStream("/types/fuel.json");

            List<ReferenceController.Fuel> fuelTypes = Arrays.asList(
                    objectMapper.readValue(inputStream, ReferenceController.Fuel[].class)
            );

            mongoTemplate
                    .remove(new Query(), ReferenceController.Fuel.class)
                    .thenMany(Flux.fromIterable(fuelTypes))
                    .flatMap(mongoTemplate::save)
                    .subscribe(
                            null,
                            error -> log.error("Error initializing fuel types", error),
                            () -> log.info("Done fuel types initialization.")
                    );

        } catch (IOException e) {
            log.error("Failed to read /types/fuel.json", e);
        }
    }

    private void initEngines() {
        log.info("Initializing engine types from JSON...");

        try {
            InputStream inputStream = getClass().getResourceAsStream("/types/engines.json");

            List<ReferenceController.Engine> engines = Arrays.asList(
                    objectMapper.readValue(inputStream, ReferenceController.Engine[].class)
            );

            mongoTemplate
                    .remove(new Query(), ReferenceController.Engine.class)
                    .thenMany(Flux.fromIterable(engines))
                    .flatMap(mongoTemplate::save)
                    .subscribe(
                            null,
                            error -> log.error("Error initializing engine types", error),
                            () -> log.info("Done engine types initialization.")
                    );

        } catch (IOException e) {
            log.error("Failed to read /types/engines.json", e);
        }
    }

    private void initGears() {
        log.info("Initializing gear types from JSON...");

        try {
            InputStream inputStream = getClass().getResourceAsStream("/types/gears.json");

            List<ReferenceController.Gear> gears = Arrays.asList(
                    objectMapper.readValue(inputStream, ReferenceController.Gear[].class)
            );

            mongoTemplate
                    .remove(new Query(), ReferenceController.Gear.class)
                    .thenMany(Flux.fromIterable(gears))
                    .flatMap(mongoTemplate::save)
                    .subscribe(
                            null,
                            error -> log.error("Error initializing gear types", error),
                            () -> log.info("Done gear types initialization.")
                    );

        } catch (IOException e) {
            log.error("Failed to read /types/gears.json", e);
        }
    }

    private void initColors() {
        log.info("Initializing color types from JSON...");

        try {
            InputStream inputStream = getClass().getResourceAsStream("/types/colors.json");

            List<ReferenceController.Color> colors = Arrays.asList(
                    objectMapper.readValue(inputStream, ReferenceController.Color[].class)
            );

            mongoTemplate
                    .remove(new Query(), ReferenceController.Color.class)
                    .thenMany(Flux.fromIterable(colors))
                    .flatMap(mongoTemplate::save)
                    .subscribe(
                            null,
                            error -> log.error("Error initializing color types", error),
                            () -> log.info("Done color types initialization.")
                    );

        } catch (IOException e) {
            log.error("Failed to read /types/colors.json", e);
        }
    }

    private void initShapes() {
        log.info("Initializing shape types from JSON...");

        try {
            InputStream inputStream = getClass().getResourceAsStream("/types/shapes.json");

            List<ReferenceController.Shape> shapes = Arrays.asList(
                    objectMapper.readValue(inputStream, ReferenceController.Shape[].class)
            );

            mongoTemplate
                    .remove(new Query(), ReferenceController.Shape.class)
                    .thenMany(Flux.fromIterable(shapes))
                    .flatMap(mongoTemplate::save)
                    .subscribe(
                            null,
                            error -> log.error("Error initializing shape types", error),
                            () -> log.info("Done shape types initialization.")
                    );

        } catch (IOException e) {
            log.error("Failed to read /types/shapes.json", e);
        }
    }

    private void initLuxury() {
        log.info("Initializing luxury types from JSON...");

        try {
            InputStream inputStream = getClass().getResourceAsStream("/types/luxury.json");

            List<ReferenceController.Luxury> luxury = Arrays.asList(
                    objectMapper.readValue(inputStream, ReferenceController.Luxury[].class)
            );

            mongoTemplate
                    .remove(new Query(), ReferenceController.Luxury.class)
                    .thenMany(Flux.fromIterable(luxury))
                    .flatMap(mongoTemplate::save)
                    .subscribe(
                            null,
                            error -> log.error("Error initializing luxury types", error),
                            () -> log.info("Done luxury types initialization.")
                    );

        } catch (IOException e) {
            log.error("Failed to read /types/luxury.json", e);
        }
    }

    private void initSafety() {
        log.info("Initializing safety types from JSON...");

        try {
            InputStream inputStream = getClass().getResourceAsStream("/types/safety.json");

            List<ReferenceController.Safety> safety = Arrays.asList(
                    objectMapper.readValue(inputStream, ReferenceController.Safety[].class)
            );

            mongoTemplate
                    .remove(new Query(), ReferenceController.Safety.class)
                    .thenMany(Flux.fromIterable(safety))
                    .flatMap(mongoTemplate::save)
                    .subscribe(
                            null,
                            error -> log.error("Error initializing safety types", error),
                            () -> log.info("Done safety types initialization.")
                    );

        } catch (IOException e) {
            log.error("Failed to read /types/safety.json", e);
        }
    }

    private void initCountries() {
        log.info("Initializing country types from JSON...");

        try {
            InputStream inputStream = getClass().getResourceAsStream("/types/countries.json");

            List<ReferenceController.Country> countries = Arrays.asList(
                    objectMapper.readValue(inputStream, ReferenceController.Country[].class)
            );

            mongoTemplate
                    .remove(new Query(), ReferenceController.Country.class)
                    .thenMany(Flux.fromIterable(countries))
                    .flatMap(mongoTemplate::save)
                    .subscribe(
                            null,
                            error -> log.error("Error initializing country types", error),
                            () -> log.info("Done country types initialization.")
                    );

        } catch (IOException e) {
            log.error("Failed to read /types/countries.json", e);
        }
    }

    private void initCities() {
        log.info("Initializing city types from JSON...");

        try {
            InputStream inputStream = getClass().getResourceAsStream("/types/cities.json");

            List<ReferenceController.City> cities = Arrays.asList(
                    objectMapper.readValue(inputStream, ReferenceController.City[].class)
            );

            mongoTemplate
                    .remove(new Query(), ReferenceController.City.class)
                    .thenMany(Flux.fromIterable(cities))
                    .flatMap(mongoTemplate::save)
                    .subscribe(
                            null,
                            error -> log.error("Error initializing city types", error),
                            () -> log.info("Done city types initialization.")
                    );

        } catch (IOException e) {
            log.error("Failed to read /types/cities.json", e);
        }
    }

    private void initCurrencies() {
        log.info("Initializing currency types from JSON...");

        try {
            InputStream inputStream = getClass().getResourceAsStream("/types/currencies.json");

            List<ReferenceController.Currency> currencies = Arrays.asList(
                    objectMapper.readValue(inputStream, ReferenceController.Currency[].class)
            );

            mongoTemplate
                    .remove(new Query(), ReferenceController.Currency.class)
                    .thenMany(Flux.fromIterable(currencies))
                    .flatMap(mongoTemplate::save)
                    .subscribe(
                            null,
                            error -> log.error("Error initializing currency types", error),
                            () -> log.info("Done currency types initialization.")
                    );

        } catch (IOException e) {
            log.error("Failed to read /types/currencies.json", e);
        }
    }

}
