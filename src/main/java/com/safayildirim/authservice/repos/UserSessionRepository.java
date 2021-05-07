package com.safayildirim.authservice.repos;

import com.safayildirim.authservice.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, String> {
    Optional<UserSession> findBySessionID(String sessionID);
}
