package com.safayildirim.authservice.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Token is invalid.");
    }
}
