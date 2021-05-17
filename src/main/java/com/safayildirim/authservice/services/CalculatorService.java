package com.safayildirim.authservice.services;

import com.safayildirim.authservice.exceptions.InvalidTokenException;
import com.safayildirim.authservice.exceptions.UserNotFoundException;
import com.safayildirim.authservice.models.User;
import com.safayildirim.authservice.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class CalculatorService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthorizationService authorizationService;

    public int calculateSum(String token, int a, int b) throws Throwable {
        if (!jwtService.validateToken(token))
            throw new InvalidTokenException();
        String username = jwtService.extractUsername(token);
        User user =
                userRepository.findByUsername(username).orElseThrow((Supplier<Throwable>) UserNotFoundException::new);
        long userId = user.getId();
        authorizationService.checkIfPermissionGranted(userId, "calculate-sum");
        return a + b;
    }

    public int calculateSubtract(String token, int a, int b) throws Throwable {
        if (!jwtService.validateToken(token))
            throw new InvalidTokenException();
        String username = jwtService.extractUsername(token);
        User user =
                userRepository.findByUsername(username).orElseThrow((Supplier<Throwable>) UserNotFoundException::new);
        long userId = user.getId();
        authorizationService.checkIfPermissionGranted(userId, "calculate-subtract");
        return a - b;
    }
}
