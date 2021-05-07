package com.safayildirim.authservice.services;

import com.safayildirim.authservice.exceptions.SessionExpiredException;
import com.safayildirim.authservice.exceptions.SessionNotFoundException;
import com.safayildirim.authservice.models.UserSession;
import com.safayildirim.authservice.repos.UserSessionRepository;
import com.safayildirim.authservice.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserSessionRepository userSessionRepository;

    public AuthenticationService(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

    public UserSession checkSessionValid(String sessionId) {
        Optional<UserSession> optionalUserSession = userSessionRepository.findBySessionID(sessionId);
        optionalUserSession.orElseThrow(SessionNotFoundException::new);
        UserSession userSession = optionalUserSession.get();
        long timeDifferenceInMinutes = DateUtils.calculateDifferenceInMinutes(userSession.getCreationDate());
        if (timeDifferenceInMinutes < 10) {
            return userSession;
        } else {
            throw new SessionExpiredException();
        }
    }
}
