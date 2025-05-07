package com.example.demo.interfaces;

import com.example.demo.domain.exception.CarNotFoundException;
import com.example.demo.domain.model.Car;
import com.example.demo.domain.model.Comment;
import com.example.demo.domain.repository.CarRepository;
import com.example.demo.domain.repository.CommentRepository;
import com.example.demo.interfaces.dto.CarSummary;
import com.example.demo.interfaces.dto.CommentForm;
import com.example.demo.interfaces.dto.PaginatedResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;

@Slf4j

@RestController()
@RequestMapping(value = "/cars")
@RequiredArgsConstructor
@Validated
public class CarController {

    private final CarRepository cars;
    private final CommentRepository comments;


    @GetMapping("/by-country/{country}")
    public Mono<PaginatedResult<CarSummary>> all(
            @PathVariable String country,
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        return this.cars.findByCountryAndKeyword(country, q, offset, limit).collectList()
                .zipWith(this.cars.countByCountryAndKeyword(country, q), PaginatedResult::new);
    }


    @PostMapping("")
    public Mono<ResponseEntity> create(@RequestBody @Valid Car car) {

        return this.cars.create(car)
                .map(saved -> created(URI.create("/cars/" + saved.getId())).build());
    }

    @GetMapping("/{id}")
    public Mono<Car> get(@PathVariable("id") String id) {
        return this.cars.visitCar(id)
                .then(cars.findById(id))
                .switchIfEmpty(Mono.error(new CarNotFoundException(id)));
    }

    @PutMapping("/{id}/stars")
    public Mono<ResponseEntity> updateStatus(@PathVariable("id") String id, @RequestBody @Valid int stars) {
        return this.cars.updateStars(id, stars)
                .handle((result, sink) -> {
                    if (true) {
                        sink.next(noContent().build());
                    } else {
                        sink.error(new CarNotFoundException(id));
                    }
                });
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity> delete(@PathVariable("id") String id) {
        return this.cars.deleteById(id)
                .handle((result, sink) -> {
                    if (true) {
                        sink.next(noContent().build());
                    } else {
                        sink.error(new CarNotFoundException(id));
                    }
                });
    }

    @GetMapping("/{id}/comments")
    public Flux<Comment> getCommentsOf(@PathVariable("id") String carId) {
        return cars.findById(carId)
                .flatMapMany(car -> Flux.fromIterable(car.getComments()))
                .flatMap(comments::findById);
    }

    @PostMapping("/{id}/comments")
    public Mono<ResponseEntity> createCommentsOf(@PathVariable("id") String id, @RequestBody @Valid CommentForm form) {
        return this.cars.addComment(id, form.content())
                .map(saved -> noContent().build());
    }

    @GetMapping("/{carId}/hasLiked")
    public Mono<Boolean> hasLikedCar(@PathVariable String carId, @RequestParam String username) {
        return cars.hasLikedCar(carId, username);
    }


}
