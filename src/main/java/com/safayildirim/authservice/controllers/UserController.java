package com.safayildirim.authservice.controllers;

import com.safayildirim.authservice.dto.ResetPasswordRequest;
import com.safayildirim.authservice.dto.UserLoginRequest;
import com.safayildirim.authservice.dto.UserLoginResponse;
import com.safayildirim.authservice.dto.UserRegisterRequest;
import com.safayildirim.authservice.services.AuthenticationService;
import com.safayildirim.authservice.services.CalculatorService;
import com.safayildirim.authservice.services.MailService;
import com.safayildirim.authservice.util.BearerTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {
    private final MailService mailService;
    private final AuthenticationService authenticationService;
    private final CalculatorService calculatorService;

    @PostMapping(value = "/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) throws Throwable {
        UserLoginResponse response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/register")
    public String register(@RequestBody UserRegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping(value = "/reset-password")
    public String resetPassword(@RequestParam String username) throws Throwable {
        return authenticationService.resetPassword(mailService, username);
    }

    @PutMapping(value = "/reset-password/{id}")
    public String resetPassword(@PathVariable String id, @RequestBody ResetPasswordRequest request) {
        return authenticationService.resetPassword(id, request);
    }

    @GetMapping(value = "/calculate-sum")
    public int calculateSum(@RequestParam int a,
                            @RequestParam int b) throws Throwable {
        String token = BearerTokenUtil.getBearerTokenHeader();
        return calculatorService.calculateSum(token, a, b);
    }

    @GetMapping(value = "/calculate-subtract")
    public int calculateSubtract(@RequestParam int a,
                                 @RequestParam int b) throws Throwable {
        String token = BearerTokenUtil.getBearerTokenHeader();
        return calculatorService.calculateSubtract(token, a, b);
    }
}
