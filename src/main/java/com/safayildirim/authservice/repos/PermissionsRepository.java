package com.safayildirim.authservice.repos;

import com.safayildirim.authservice.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionsRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findAllByUserIdAndApi(long userId, String api);
}
