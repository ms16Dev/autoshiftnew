/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.domain.repository;


import com.example.demo.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author hantsy
 */
public interface UserRepository {

    Mono<User> findByUsername(String username);

    Mono<User> findByEmail(String email);

    Mono<User> create(User user);

    Mono<Long> deleteAll();

    Flux<User> findAll();

    Mono<User> save(User user);
}
