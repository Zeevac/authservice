package com.safayildirim.authservice.controllers;

import com.safayildirim.authservice.dto.UserLoginInfoResponse;
import com.safayildirim.authservice.dto.UserLoginRequest;
import com.safayildirim.authservice.dto.UserLoginResponse;
import com.safayildirim.authservice.dto.UserRegisterRequest;
import com.safayildirim.authservice.services.MailService;
import com.safayildirim.authservice.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final MailService mailService;
    private final UserService userService;

    public UserController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) throws Throwable {
        UserLoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLoginInfoResponse> login(@RequestParam(value = "sessionId") String sessionID) {
        UserLoginInfoResponse response = userService.login(sessionID);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String register(@RequestBody UserRegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping(value = "/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String resetPassword(@RequestParam String username) throws Throwable {
        return userService.resetPassword(mailService, username);
    }
}
