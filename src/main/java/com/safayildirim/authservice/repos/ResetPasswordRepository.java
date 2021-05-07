package com.safayildirim.authservice.repos;

import com.safayildirim.authservice.models.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, String> {
}
