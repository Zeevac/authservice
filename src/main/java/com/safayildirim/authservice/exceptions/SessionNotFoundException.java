package com.safayildirim.authservice.exceptions;

public class SessionNotFoundException extends RuntimeException {
    public SessionNotFoundException() {
        super("No session found");
    }
}
