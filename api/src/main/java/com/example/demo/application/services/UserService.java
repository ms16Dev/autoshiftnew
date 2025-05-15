package com.example.demo.application.services;

import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.interfaces.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;


    public Mono<User> registerUser(RegisterRequest request) {
        Mono<Boolean> emailExists = userRepository.findByEmail(request.getEmail()).hasElement();
        Mono<Boolean> usernameExists = userRepository.findByUsername(request.getUsername()).hasElement();

        return Mono.zip(emailExists, usernameExists)
                .flatMap(results -> {
                    boolean emailTaken = results.getT1();
                    boolean usernameTaken = results.getT2();

                    if (emailTaken && usernameTaken) {
                        return Mono.error(new IllegalStateException("Email and Username are already registered"));
                    } else if (emailTaken) {
                        return Mono.error(new IllegalStateException("Email is already registered"));
                    } else if (usernameTaken) {
                        return Mono.error(new IllegalStateException("Username is already taken"));
                    }

                    // Both email and username are unique — continue to register
                    User newUser = new User();
                    newUser.setEmail(request.getEmail());
                    newUser.setUsername(request.getUsername());
                    newUser.setPassword(passwordEncoder.encode(request.getPassword()));
                    newUser.setRoles(List.of("ROLE_USER"));
                    newUser.setActive(true);

                    return userRepository.save(newUser)
                            .flatMap(user -> profileService.createEmptyProfile(user.getUsername()).thenReturn(user));
                });
    }
}

