/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.interfaces;


import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository users;
    private final PasswordEncoder passwordEncoder; // Autowire PasswordEncoder


    @GetMapping("/users/{username}")
    public Mono<User> get(@PathVariable() String username) {
        return this.users.findByUsername(username);
    }

    @GetMapping("/users")
    public Flux<User> getAllUsers() {
        return this.users.findAll();
    }

    @PostMapping("/users")
    public Mono<ResponseEntity<String>> addUser(@RequestBody @Valid User newUser) {
        return users.findByUsername(newUser.getUsername())
                .flatMap(existingUser ->
                        Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
                                .body("Username already exists"))
                )
                .switchIfEmpty(
                        Mono.defer(() -> {
                            // Encode password before saving
                            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
                            return users.create(newUser)  // Changed from create() to save()
                                    .thenReturn(ResponseEntity.ok("User created successfully"));
                        })
                );
    }

    @PutMapping("/users/{username}")
    public Mono<ResponseEntity<String>> updateUserByUsername(
            @PathVariable String username,
            @RequestBody User updatedUser) {

        return users.findByUsername(username)
                .flatMap(existingUser -> {
                    // Update only non-null or non-empty fields
                    if (updatedUser.getEmail() != null) {
                        existingUser.setEmail(updatedUser.getEmail());
                    }

                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }

                    if (updatedUser.getRoles() != null && !updatedUser.getRoles().isEmpty()) {
                        existingUser.setRoles(updatedUser.getRoles());
                    }

                    // Even though `active` is primitive, you may want to explicitly set it
                    existingUser.setActive(updatedUser.isActive());

                    return users.save(existingUser)
                            .thenReturn(ResponseEntity.ok("User updated successfully"));
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found")));
    }

}
