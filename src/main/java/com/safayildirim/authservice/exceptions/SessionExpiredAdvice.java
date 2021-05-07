package com.safayildirim.authservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SessionExpiredAdvice {
    @ResponseBody
    @ExceptionHandler(SessionExpiredException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    String sessionExpiredHandler(SessionExpiredException e) {
        return e.getMessage();
    }
}
