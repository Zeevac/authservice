package com.safayildirim.authservice.services;

import com.safayildirim.authservice.dto.*;
import com.safayildirim.authservice.exceptions.LinkExpiredException;
import com.safayildirim.authservice.exceptions.SessionExpiredException;
import com.safayildirim.authservice.exceptions.SessionNotFoundException;
import com.safayildirim.authservice.exceptions.UserNotExistException;
import com.safayildirim.authservice.models.CustomUser;
import com.safayildirim.authservice.models.ResetPassword;
import com.safayildirim.authservice.models.User;
import com.safayildirim.authservice.models.UserSession;
import com.safayildirim.authservice.repos.ResetPasswordRepository;
import com.safayildirim.authservice.repos.UserRepository;
import com.safayildirim.authservice.repos.UserSessionRepository;
import com.safayildirim.authservice.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class UserService {
    private final UserRepository repository;
    private final UserSessionRepository userSessionRepository;
    private final ResetPasswordRepository resetPasswordRepository;

    public UserService(UserRepository repository, UserSessionRepository userSessionRepository, ResetPasswordRepository resetPasswordRepository) {
        this.repository = repository;
        this.userSessionRepository = userSessionRepository;
        this.resetPasswordRepository = resetPasswordRepository;
    }

    public UserLoginInfoResponse login(String sessionID) {
        UserLoginInfoResponse response = new UserLoginInfoResponse();
        CustomUser customUser = new CustomUser();
        Optional<UserSession> optionalUserSession = userSessionRepository.findBySessionID(sessionID);
        optionalUserSession.orElseThrow(SessionNotFoundException::new);
        UserSession userSession = optionalUserSession.get();
        BeanUtils.copyProperties(userSession.getUser(), customUser);
        long timeDifferenceInMinutes = DateUtils.calculateDifferenceInMinutes(userSession.getCreationDate());
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
        String uuid = UUID.randomUUID().toString();
        String generatedLink = String.format("localhost:8080/reset-password/%s", uuid);
        resetPasswordRepository.save(new ResetPassword(uuid, username, LocalDateTime.now()));
        mailService.sendMail(email, "Reset Password", generatedLink);
        return "index";
    }

    public String resetPassword(String id, ResetPasswordRequest request) {
        String newPassword = request.getNewPassword();
        ResetPassword resetPassword = resetPasswordRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
        long timeDifferenceInMinutes = DateUtils.calculateDifferenceInMinutes(resetPassword.getDate());
        if (timeDifferenceInMinutes > 1) throw new LinkExpiredException();
        String username = resetPassword.getUsername();
        User user = repository.findByUsername(username).orElseThrow(UserNotExistException::new);
        user.setPassword(newPassword);
        repository.save(user);
        return "Success";
    }
}
