package com.safayildirim.authservice.exceptions.advices;

import com.safayildirim.authservice.exceptions.LinkExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LinkExpiredAdvice {
    @ResponseBody
    @ExceptionHandler(LinkExpiredException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    String linkExpiredHandler(LinkExpiredException e) {
        return e.getMessage();
    }
}
