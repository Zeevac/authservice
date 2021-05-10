package com.safayildirim.authservice.exceptions.advices;

import com.safayildirim.authservice.exceptions.UsernameAlreadyTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UsernameAlreadyTakenAdvice {
    @ResponseBody
    @ExceptionHandler(UsernameAlreadyTakenException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String usernameTakenHandler(UsernameAlreadyTakenException e) {
        return e.getMessage();
    }
}
