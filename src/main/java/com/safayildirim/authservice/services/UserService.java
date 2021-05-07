package com.safayildirim.authservice.services;

import com.safayildirim.authservice.dto.UserLoginInfoResponse;
import com.safayildirim.authservice.dto.UserLoginRequest;
import com.safayildirim.authservice.dto.UserLoginResponse;
import com.safayildirim.authservice.dto.UserRegisterRequest;
import com.safayildirim.authservice.exceptions.SessionExpiredException;
import com.safayildirim.authservice.exceptions.SessionNotFoundException;
import com.safayildirim.authservice.exceptions.UserNotExistException;
import com.safayildirim.authservice.models.CustomUser;
import com.safayildirim.authservice.models.User;
import com.safayildirim.authservice.models.UserSession;
import com.safayildirim.authservice.repos.UserRepository;
import com.safayildirim.authservice.repos.UserSessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class UserService {
    private final UserRepository repository;
    private final UserSessionRepository userSessionRepository;

    public UserService(UserRepository repository, UserSessionRepository userSessionRepository) {
        this.repository = repository;
        this.userSessionRepository = userSessionRepository;
    }

    public UserLoginInfoResponse login(String sessionID) {
        UserLoginInfoResponse response = new UserLoginInfoResponse();
        CustomUser customUser = new CustomUser();
        Optional<UserSession> optionalUserSession = userSessionRepository.findBySessionID(sessionID);
        optionalUserSession.orElseThrow(SessionNotFoundException::new);
        UserSession userSession = optionalUserSession.get();
        BeanUtils.copyProperties(userSession.getUser(), customUser);
        LocalDateTime sessionDate = userSession.getCreationDate();
        LocalDateTime currentDate = LocalDateTime.now();
        long timeDifferenceInMinutes = ((Timestamp.valueOf(currentDate).getTime() - Timestamp.valueOf(sessionDate).getTime()) / (1000 * 60)) % 60;
        if (timeDifferenceInMinutes < 10) {
            response.setUser(customUser);
        } else {
            throw new SessionExpiredException();
        }
        return response;
    }

    public UserLoginResponse login(UserLoginRequest request) throws Throwable {
        String username = request.getUsername();
        String password = request.getPassword();
        User user = repository.findByUsernameAndPassword(username, password).orElseThrow((Supplier<Throwable>) UserNotExistException::new);
        String sessionID = UUID.randomUUID().toString();
        UserLoginResponse response = new UserLoginResponse(sessionID);
        userSessionRepository.save(new UserSession(sessionID, user, LocalDateTime.now()));
        return response;
    }

    public String register(UserRegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();
        repository.save(new User(username, password, email));
        return "index";
    }

    public String resetPassword(MailService mailService, String username) throws Throwable {
        User user = repository.findByUsername(username).orElseThrow((Supplier<Throwable>) UserNotExistException::new);
        String email = user.getEmail();
        mailService.sendMail(email, "Reset Password", "");
        return "index";
    }
}
