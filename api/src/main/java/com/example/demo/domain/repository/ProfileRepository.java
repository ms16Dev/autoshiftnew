package com.example.demo.domain.repository;

import com.example.demo.domain.model.UserProfile;
import com.example.demo.interfaces.dto.ProfileSummary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProfileRepository {

    Mono<UserProfile> findByUsername(String username);

    Mono<UserProfile> create(UserProfile profile);

    Mono<UserProfile> save(UserProfile profile);

    Mono<Long> deleteAll();

    Flux<UserProfile> findAll();

    Flux<ProfileSummary> findByCountryAndKeyword(String country, String keyword, int offset, int limit);

    Mono<Long> countByCountryAndKeyword(String country, String keyword);



}

