package com.example.demo.domain.exception;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(String id) {
        super("Profile #" + id + " was not found.");
    }
}
