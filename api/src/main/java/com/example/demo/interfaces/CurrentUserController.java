/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.interfaces;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Map;

/**
 * @author hantsy
 */
@RestController
@RequestMapping("/me")
public class CurrentUserController {

    @GetMapping("")
    public Mono<ResponseEntity<Map<String, Object>>> current(@AuthenticationPrincipal Mono<Principal> principal) {
        return principal
                .map(user -> {
                    Map<String, Object> body = Map.of(
                            "name", user.getName(),
                            "roles", AuthorityUtils.authorityListToSet(((Authentication) user).getAuthorities())
                    );
                    return ResponseEntity.ok(body);
                })
                .switchIfEmpty(Mono.just(
                        ResponseEntity.status(440) // Or 419 if you prefer
                                .body(Map.of("error", "Session expired"))
                ));
    }

}
