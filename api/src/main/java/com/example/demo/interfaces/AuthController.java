package com.example.demo.interfaces;

import com.example.demo.application.services.UserService;
import com.example.demo.domain.model.User;
import com.example.demo.interfaces.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final KeyPair keyPair;

    @Autowired
    public AuthController(UserService userService) throws NoSuchAlgorithmException {
        this.userService = userService;
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        this.keyPair = generator.generateKeyPair();
    }

    @GetMapping("/public-key")
    public ResponseEntity<String> getPublicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        return ResponseEntity.ok()
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .body(publicKeyBase64);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> register(@RequestBody RegisterRequest request) {
        try {
            // Decrypt the password here before passing it to the service


            // Pass the modified request to UserService
            return userService.registerUser(request);
        } catch (Exception e) {
            return Mono.error(new RuntimeException("Password decryption failed"));
        }
    }

    public String decryptPassword(String encryptedPassword) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
        return new String(cipher.doFinal(decodedBytes));
    }

}
