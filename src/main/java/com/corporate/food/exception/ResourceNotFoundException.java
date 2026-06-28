package com.corporate.food.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String workingWeek, String id, Long id1) {
    }
}
