package com.example.demo.domain.exception;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(String id) {
        super("Car #" + id + " was not found.");
    }
}
