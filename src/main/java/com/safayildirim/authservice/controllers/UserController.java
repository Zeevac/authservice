package com.safayildirim.authservice.controllers;

import com.safayildirim.authservice.dto.UserLoginRequest;
import com.safayildirim.authservice.dto.UserRegisterRequest;
import com.safayildirim.authservice.dto.UserRegisterResponse;
import com.safayildirim.authservice.exceptions.UserNotExistException;
import com.safayildirim.authservice.models.User;
import com.safayildirim.authservice.repos.UserRepository;
import com.safayildirim.authservice.services.MailService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@RestController
public class UserController {
    private final UserRepository repository;
    private final MailService mailService;

    public UserController(UserRepository repository) {
        this.repository = repository;
        this.mailService = new MailService();
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request) throws Throwable {
        String username = request.getUsername();
        String password = request.getPassword();
        repository.findByUsernameAndPassword(username, password).orElseThrow((Supplier<Throwable>) UserNotExistException::new);
        return ResponseEntity.ok("Hello");
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRegisterResponse> register(@RequestBody UserRegisterRequest request) {
        String userName = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();
        User registeredUser = repository.save(new User(userName, password, email));
        UserRegisterResponse response = new UserRegisterResponse();
        response.setId(registeredUser.getId());
        response.setMessage("Registration is success");
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRegisterResponse> register(@RequestParam String username) throws Throwable {
        User user = repository.findByUsername(username).orElseThrow((Supplier<Throwable>) UserNotExistException::new);
        String email = user.getEmail();
        mailService.sendMail(email, "Reset Password", "");
    }
}
