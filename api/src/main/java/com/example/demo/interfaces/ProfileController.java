package com.example.demo.interfaces;

import com.example.demo.domain.exception.ProfileNotFoundException;
import com.example.demo.domain.model.UserProfile;
import com.example.demo.domain.repository.ProfileRepository;
import com.example.demo.interfaces.dto.PaginatedResult;
import com.example.demo.interfaces.dto.ProfileSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileRepository profiles;


    @GetMapping("/by-country/{country}")
    public Mono<PaginatedResult<ProfileSummary>> all(
            @PathVariable String country,
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        return this.profiles.findByCountryAndKeyword(country, q, offset, limit).collectList()
                .zipWith(this.profiles.countByCountryAndKeyword(country, q), PaginatedResult::new);
    }


    @GetMapping("/{username}")
    public Mono<UserProfile> get(@PathVariable("username") String id) {
        return this.profiles.findByUsername(id)
                .switchIfEmpty(Mono.error(new ProfileNotFoundException(id)));
    }

    @PutMapping("/{username}")
    public Mono<ResponseEntity<String>> updateUserProfile(
            @PathVariable String username,
            @RequestBody UserProfile updatedProfile) {

        return profiles.findByUsername(username)
                .flatMap(existingProfile -> {
                    // Update fields if provided
                    if (updatedProfile.getName() != null) {
                        existingProfile.setName(updatedProfile.getName());
                    }
                    if (updatedProfile.getAvatarUrl() != null) {
                        existingProfile.setAvatarUrl(updatedProfile.getAvatarUrl());
                    }
                    if (updatedProfile.getCoverUrl() != null) {
                        existingProfile.setCoverUrl(updatedProfile.getCoverUrl());
                    }
                    if (updatedProfile.getSubtitle() != null) {
                        existingProfile.setSubtitle(updatedProfile.getSubtitle());
                    }
                    if (updatedProfile.getInfo() != null) {
                        existingProfile.setInfo(updatedProfile.getInfo());
                    }
                    if (updatedProfile.getCountry() != null) {
                        existingProfile.setCountry(updatedProfile.getCountry());
                    }
                    if (updatedProfile.getLocation() != null) {
                        existingProfile.setLocation(updatedProfile.getLocation());
                    }
                    if (updatedProfile.getContact() != null) {
                        existingProfile.setContact(updatedProfile.getContact());
                    }

                    existingProfile.setDealer(updatedProfile.isDealer());
                    existingProfile.setComplete(updatedProfile.isComplete());

                    return profiles.save(existingProfile)
                            .thenReturn(ResponseEntity.ok("Profile updated successfully"));
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Profile not found")));
    }
}
