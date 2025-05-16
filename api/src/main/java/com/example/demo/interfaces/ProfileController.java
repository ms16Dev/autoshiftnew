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

        return this.profiles.findByCountry(country, offset, limit).collectList()
                .zipWith(this.profiles.countByCountry(country), PaginatedResult::new);
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
                    // Overwrite the existing profile with the new data
                    updatedProfile.setId(existingProfile.getId()); // Ensure existing DB ID is retained
                    updatedProfile.setUsername(username);          // Retain the path variable username

                    return profiles.save(updatedProfile)
                            .thenReturn(ResponseEntity.ok("Profile updated successfully"));
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Profile not found")));
    }

}
