package com.example.demo.domain.repository;

import com.example.demo.domain.model.Car;
import com.example.demo.interfaces.dto.CarSummary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CarRepository {


    Flux<Car> findAll();

    Flux<CarSummary> findByKeyword(String keyword, int offset, int limit);

    Mono<Long> countByKeyword(String keyword);

    Mono<Car> findById(String id);

    Mono<Car> create(Car car);

    Mono<Boolean> update(String id, String title, String content);

    Mono<Boolean> updateStars(String id, int stars);

    Mono<Boolean> deleteById(String id);

    Mono<Long> deleteAll();

    Mono<Boolean> addComment(String id, String content);

    Mono<Boolean> removeComment(String id, String commentId);

    Mono<Boolean> visitCar(String id);

    Mono<Boolean> likeCar(String carId, String username);

    Mono<Boolean> unLikeCar(String carId, String username);

    Mono<Boolean> shareCar(String carId, String username);
}
