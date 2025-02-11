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

    public Mono<User> registerUser(RegisterRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .flatMap(existingUser -> Mono.error(new RuntimeException("Username already exists")))
                .switchIfEmpty(
                        Mono.defer(() -> {
                            User newUser = new User();
                            newUser.setUsername(request.getUsername());
                            newUser.setPassword(passwordEncoder.encode(request.getPassword()));
                            newUser.setEmail(request.getEmail());
                            newUser.setActive(true);
                            newUser.setRoles(List.of("ROLE_USER"));
                            return userRepository.create(newUser); // Ensure this returns Mono<User>
                        })
                )
                .cast(User.class);
    }

}
