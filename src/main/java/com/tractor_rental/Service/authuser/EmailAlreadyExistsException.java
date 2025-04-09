package com.tractor_rental.Service.authuser;

// Custom exception
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
