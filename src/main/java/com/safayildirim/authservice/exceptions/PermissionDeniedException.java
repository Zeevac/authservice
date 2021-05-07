package com.safayildirim.authservice.exceptions;

public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException() {
        super("User does not have access to the this api.");
    }
}
