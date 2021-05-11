package com.safayildirim.authservice.services;

import com.safayildirim.authservice.models.UserSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CalculatorService {
    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;

    public int calculateSum(String sessionId, int a, int b) {
        UserSession userSession = authenticationService.checkSessionValid(sessionId);
        long userId = userSession.getUser().getId();
        authorizationService.checkIfPermissionGranted(userId, "calculate-sum");
        return a + b;
    }

    public int calculateSubtract(String sessionId, int a, int b) {
        UserSession userSession = authenticationService.checkSessionValid(sessionId);
        long userId = userSession.getUser().getId();
        authorizationService.checkIfPermissionGranted(userId, "calculate-subtract");
        return a - b;
    }
}
