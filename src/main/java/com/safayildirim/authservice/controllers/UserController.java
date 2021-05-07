package com.safayildirim.authservice.controllers;

import com.safayildirim.authservice.dto.*;
import com.safayildirim.authservice.services.CalculatorService;
import com.safayildirim.authservice.services.MailService;
import com.safayildirim.authservice.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final MailService mailService;
    private final UserService userService;
    private final CalculatorService calculatorService;

    public UserController(UserService userService, MailService mailService, CalculatorService calculatorService) {
        this.userService = userService;
        this.mailService = mailService;
        this.calculatorService = calculatorService;
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

    @PutMapping(value = "/reset-password/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String resetPassword(@PathVariable String id, @RequestBody ResetPasswordRequest request) {
        return userService.resetPassword(id, request);
    }

    @GetMapping(value = "/calculate-sum")
    public int calculateSum(@RequestParam(value = "sessionId") String sessionID, @RequestParam int a, @RequestParam int b) {
        return calculatorService.calculateSum(sessionID, a, b);
    }
}
