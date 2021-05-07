package com.safayildirim.authservice.services;

import com.safayildirim.authservice.repos.PermissionsRepository;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    private PermissionsRepository permissionsRepository;
    private final AuthService authService;

    public CalculatorService(PermissionsRepository permissionsRepository, AuthService authService) {
        this.permissionsRepository = permissionsRepository;
        this.authService = authService;
    }

    public int calculateSum(String sessionId, int a, int b) {
        authService.checkSessionValid(sessionId);
        return a + b;
    }
}
