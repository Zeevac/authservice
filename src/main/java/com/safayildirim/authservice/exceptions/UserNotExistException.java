package com.safayildirim.authservice.exceptions;

public class UserNotExistException extends RuntimeException{
    public UserNotExistException() {
        super("User does not exist.");
    }
}
