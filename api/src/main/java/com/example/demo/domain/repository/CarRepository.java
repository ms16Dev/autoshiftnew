package com.example.demo.domain.repository;

import com.example.demo.domain.model.Car;
import com.example.demo.domain.model.Status;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CarRepository {


    Flux<Car> findAll();

    Mono<Long> countByKeyword(String keyword);

    Mono<Car> findById(String id);

    Mono<Car> create(Car car);

    Mono<Boolean> update(String id, String title, String content);

    Mono<Boolean> updateStatus(String id, Status status);

    Mono<Boolean> deleteById(String id);

    Mono<Long> deleteAll();

    Mono<Boolean> addComment(String id, String content);

    Mono<Boolean> removeComment(String id, String commentId);

}
