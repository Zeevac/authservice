package com.safayildirim.authservice.exceptions;

public class LinkExpiredException extends RuntimeException {
    public LinkExpiredException() {
        super("Validation link expired.");
    }
}
