package com.safayildirim.authservice.controllers;

import com.safayildirim.authservice.dto.*;
import com.safayildirim.authservice.services.CalculatorService;
import com.safayildirim.authservice.services.MailService;
import com.safayildirim.authservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {
    private final MailService mailService;
    private final UserService userService;
    private final CalculatorService calculatorService;


    @PostMapping(value = "/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) throws Throwable {
        UserLoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/login")
    public ResponseEntity<UserLoginInfoResponse> login(@RequestParam(value = "sessionId") String sessionID) {
        UserLoginInfoResponse response = userService.login(sessionID);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/register")
    public String register(@RequestBody UserRegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping(value = "/reset-password")
    public String resetPassword(@RequestParam String username) throws Throwable {
        return userService.resetPassword(mailService, username);
    }

    @PutMapping(value = "/reset-password/{id}")
    public String resetPassword(@PathVariable String id, @RequestBody ResetPasswordRequest request) {
        return userService.resetPassword(id, request);
    }

    @GetMapping(value = "/calculate-sum")
    public int calculateSum(@RequestParam(value = "sessionId") String sessionId, @RequestParam int a, @RequestParam int b) {
        return calculatorService.calculateSum(sessionId, a, b);
    }

    @GetMapping(value = "/calculate-subtract")
    public int calculateSubtract(@RequestParam(value = "sessionId") String sessionId, @RequestParam int a, @RequestParam int b) {
        return calculatorService.calculateSubtract(sessionId, a, b);
    }
}
