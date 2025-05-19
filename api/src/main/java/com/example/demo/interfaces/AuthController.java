package com.example.demo.interfaces;

import com.example.demo.application.services.EmailService;
import com.example.demo.application.services.UserService;
import com.example.demo.application.services.VerificationTokenService;
import com.example.demo.interfaces.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;

    @Autowired
    public AuthController(UserService userService,
                          VerificationTokenService verificationTokenService,
                          EmailService emailService
    ) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
        this.emailService = emailService;
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<ResponseEntity<Map<String, String>>> register(@RequestBody RegisterRequest request) {
        System.out.println("Register request email: " + request.getEmail());  // Debug log

        return userService.registerUser(request)
                .flatMap(user -> {
                    System.out.println("User email after registration: " + user.getEmail());  // Debug log

                    return verificationTokenService.generateVerificationToken(user.getUsername())
                            .flatMap(token -> {
                                System.out.println("Token generated: " + token.getToken());

                                return emailService.sendVerificationEmail(user.getEmail(), token.getToken())
                                        .thenReturn(ResponseEntity.accepted()
                                                .body(Map.of(
                                                        "message", "Registration successful. Verification email sent.",
                                                        "email", user.getEmail()
                                                )));
                            });
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest()
                        .body(Map.of("error", e.getMessage()))));
    }



    @GetMapping("/verify")
    public Mono<ResponseEntity<Map<String, String>>> verifyUser(@RequestParam("token") String token) {
        return verificationTokenService.verifyUser(token)
                .map(user -> {
                    Map<String, String> successMessage = Map.of(
                            "message", "Email verification successful. You may now log in."
                    );
                    return ResponseEntity.ok(successMessage);
                })
                .onErrorResume(e -> {
                    Map<String, String> errorMessage = Map.of("error", e.getMessage());
                    return Mono.just(ResponseEntity.badRequest().body(errorMessage));
                });
    }


}
