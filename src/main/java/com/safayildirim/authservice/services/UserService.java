package com.safayildirim.authservice.services;

import com.safayildirim.authservice.dto.UserLoginRequest;
import com.safayildirim.authservice.dto.UserLoginResponse;
import com.safayildirim.authservice.dto.UserRegisterRequest;
import com.safayildirim.authservice.exceptions.UserNotExistException;
import com.safayildirim.authservice.models.User;
import com.safayildirim.authservice.models.UserSession;
import com.safayildirim.authservice.repos.UserRepository;
import com.safayildirim.authservice.repos.UserSessionRepository;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Controller
public class UserService {
    private final UserRepository repository;
    private final UserSessionRepository userSessionRepository;

    public UserService(UserRepository repository, UserSessionRepository userSessionRepository) {
        this.repository = repository;
        this.userSessionRepository = userSessionRepository;
    }

    public UserLoginResponse login(UserLoginRequest request) throws Throwable {
        String username = request.getUsername();
        String password = request.getPassword();
        UserLoginResponse response = null;
        repository.findByUsernameAndPassword(username, password).orElseThrow((Supplier<Throwable>) UserNotExistException::new);
        Optional<UserSession> optionalUserSession = userSessionRepository.findByUsername(username);
        if (optionalUserSession.isPresent()) {
            UserSession userSession = optionalUserSession.get();
            Date sessionDate = userSession.getCreationDate();
            Date currentDate = new Date();
            long timeDifferenceInMinutes = ((currentDate.getTime() - sessionDate.getTime()) / (1000 * 60)) % 60;
            if (timeDifferenceInMinutes < 10) {
                response = new UserLoginResponse(userSession.getSessionID());
            }
        } else {
            String sessionID = UUID.randomUUID().toString();
            response = new UserLoginResponse(sessionID);
            userSessionRepository.save(new UserSession(sessionID, username, new Date()));
        }
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
