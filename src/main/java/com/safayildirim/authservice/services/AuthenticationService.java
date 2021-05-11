package com.safayildirim.authservice.services;

import com.safayildirim.authservice.exceptions.LinkExpiredException;
import com.safayildirim.authservice.exceptions.SessionNotFoundException;
import com.safayildirim.authservice.models.UserSession;
import com.safayildirim.authservice.repos.UserSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserSessionRepository userSessionRepository;

    public UserSession checkSessionValid(String sessionId) {
        Optional<UserSession> optionalUserSession = userSessionRepository.findBySessionID(sessionId);
        optionalUserSession.orElseThrow(SessionNotFoundException::new);
        UserSession userSession = optionalUserSession.get();
        if (LocalDateTime.now().isAfter(userSession.getExpireDate())) {
            throw new LinkExpiredException();
        }
        return userSession;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
