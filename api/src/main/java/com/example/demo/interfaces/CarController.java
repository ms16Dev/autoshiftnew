package com.example.demo.interfaces;

import com.example.demo.domain.model.Car;
import com.example.demo.domain.repository.CarRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.http.ResponseEntity.created;
@Slf4j

@RestController()
@RequestMapping(value = "/cars")
@RequiredArgsConstructor
@Validated
public class CarController {

    private final CarRepository cars;


    @PostMapping("")
    public Mono<ResponseEntity> create(@RequestBody @Valid Car car) {

        log.debug("-=-=-=-=-=-=-=-=-=-=-Before inserting car: {}", car);  // Debug level to ensure the method is hit
        return this.cars.create(car)
                .map(saved -> created(URI.create("/cars/" + saved.getId())).build());
    }

}
