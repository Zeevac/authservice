package com.safayildirim.authservice.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User does not exist.");
    }
}
