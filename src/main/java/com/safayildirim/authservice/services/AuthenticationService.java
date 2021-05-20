package com.safayildirim.authservice.services;

import com.safayildirim.authservice.dto.*;
import com.safayildirim.authservice.exceptions.InvalidTokenException;
import com.safayildirim.authservice.exceptions.LinkExpiredException;
import com.safayildirim.authservice.exceptions.UserNotFoundException;
import com.safayildirim.authservice.exceptions.UsernameAlreadyTakenException;
import com.safayildirim.authservice.models.CustomUser;
import com.safayildirim.authservice.models.ResetPassword;
import com.safayildirim.authservice.models.User;
import com.safayildirim.authservice.repos.ResetPasswordRepository;
import com.safayildirim.authservice.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final ResetPasswordRepository resetPasswordRepository;
    private final JWTService jwtService;
    private PasswordEncoder passwordEncoder;

    public UserLoginResponse login(UserLoginRequest request) throws Throwable {
        String username = request.getUsername();
        String password = request.getPassword();
        User user =
                userRepository.findByUsername(username).orElseThrow((Supplier<Throwable>) UserNotFoundException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new UserNotFoundException();
        String token = jwtService.generateToken(user);
        return new UserLoginResponse(token);
    }

    public String register(UserRegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        userRepository.findByUsername(username).ifPresent(user -> {
            throw new UsernameAlreadyTakenException();
        });
        password = passwordEncoder.encode(password);
        String email = request.getEmail();
        userRepository.save(new User(username, password, email));
        return "index";
    }

    public String resetPassword(MailService mailService, String username) throws Throwable {
        User user =
                userRepository.findByUsername(username).orElseThrow((Supplier<Throwable>) UserNotFoundException::new);
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
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        newPassword = passwordEncoder.encode(newPassword);
        user.setPassword(newPassword);
        userRepository.save(user);
        return "Success";
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
