package com.safayildirim.authservice.exceptions.advices;

import com.safayildirim.authservice.exceptions.SessionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SessionNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(SessionNotFoundException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    String sessionNotFoundHandler(SessionNotFoundException e) {
        return e.getMessage();
    }
}
