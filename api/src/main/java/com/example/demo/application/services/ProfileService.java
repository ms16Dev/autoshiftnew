package com.example.demo.application.services;

import com.example.demo.domain.model.UserProfile;
import com.example.demo.domain.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public Mono<UserProfile> createEmptyProfile(String username) {
        UserProfile profile = UserProfile.builder()
                .username(username)
                .name(username)
                .build();

        return profileRepository.create(profile);
    }
}

