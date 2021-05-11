package com.safayildirim.authservice.services;

import com.safayildirim.authservice.dto.*;
import com.safayildirim.authservice.exceptions.LinkExpiredException;
import com.safayildirim.authservice.exceptions.UserNotFoundException;
import com.safayildirim.authservice.exceptions.UsernameAlreadyTakenException;
import com.safayildirim.authservice.models.CustomUser;
import com.safayildirim.authservice.models.ResetPassword;
import com.safayildirim.authservice.models.User;
import com.safayildirim.authservice.models.UserSession;
import com.safayildirim.authservice.repos.ResetPasswordRepository;
import com.safayildirim.authservice.repos.UserRepository;
import com.safayildirim.authservice.repos.UserSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class UserService{
    private final UserRepository repository;
    private final UserSessionRepository userSessionRepository;
    private final ResetPasswordRepository resetPasswordRepository;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    public UserLoginInfoResponse login(String sessionID) {
        UserLoginInfoResponse response = new UserLoginInfoResponse();
        CustomUser customUser = new CustomUser();
        UserSession userSession = authenticationService.checkSessionValid(sessionID);
        BeanUtils.copyProperties(userSession.getUser(), customUser);
        response.setUser(customUser);
        return response;
    }

    public UserLoginResponse login(UserLoginRequest request) throws Throwable {
        String username = request.getUsername();
        String password = request.getPassword();
        User user = repository.findByUsername(username).orElseThrow((Supplier<Throwable>) UserNotFoundException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new UserNotFoundException();
        String sessionID = UUID.randomUUID().toString();
        UserLoginResponse response = new UserLoginResponse(sessionID);
        userSessionRepository.save(new UserSession(sessionID, user, LocalDateTime.now().plusMinutes(10)));
        return response;
    }

    public String register(UserRegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        repository.findByUsername(username).ifPresent(user -> {
            throw new UsernameAlreadyTakenException();
        });
        password = passwordEncoder.encode(password);
        String email = request.getEmail();
        repository.save(new User(username, password, email));
        return "index";
    }

    public String resetPassword(MailService mailService, String username) throws Throwable {
        User user = repository.findByUsername(username).orElseThrow((Supplier<Throwable>) UserNotFoundException::new);
        String email = user.getEmail();
        String uuid = UUID.randomUUID().toString();
        String generatedLink = String.format("localhost:8080/reset-password/%s", uuid);
        resetPasswordRepository.save(new ResetPassword(uuid, username, LocalDateTime.now().plusMinutes(10)));
        mailService.sendMail(email, "Reset Password", generatedLink);
        return "index";
    }

    public String resetPassword(String id, ResetPasswordRequest request) {
        String newPassword = request.getNewPassword();
        ResetPassword resetPassword = resetPasswordRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
        if (LocalDateTime.now().isAfter(resetPassword.getExpireDate())) {
            throw new LinkExpiredException();
        }
        String username = resetPassword.getUsername();
        User user = repository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        newPassword = passwordEncoder.encode(newPassword);
        user.setPassword(newPassword);
        repository.save(user);
        return "Success";
    }

}
