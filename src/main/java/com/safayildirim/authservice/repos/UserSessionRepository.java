package com.safayildirim.authservice.repos;

import com.safayildirim.authservice.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, String> {
    Optional<UserSession> findByUsername(String username);
}
