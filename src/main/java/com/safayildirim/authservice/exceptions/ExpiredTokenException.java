package com.safayildirim.authservice.exceptions;

public class ExpiredTokenException extends RuntimeException{
    public ExpiredTokenException() {
        super("This token is expired.");
    }
}
