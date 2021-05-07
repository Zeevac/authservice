package com.safayildirim.authservice.exceptions;

public class SessionExpiredException extends RuntimeException{
    public SessionExpiredException() {
        super("Session expired.");
    }
}
