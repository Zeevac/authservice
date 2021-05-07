package com.safayildirim.authservice.repos;

import com.safayildirim.authservice.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionsRepository extends JpaRepository<Permission, Long> {
}
