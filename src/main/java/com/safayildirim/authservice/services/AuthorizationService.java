package com.safayildirim.authservice.services;

import com.safayildirim.authservice.exceptions.PermissionDeniedException;
import com.safayildirim.authservice.models.Permission;
import com.safayildirim.authservice.repos.PermissionsRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private final PermissionsRepository permissionsRepository;

    public AuthorizationService(PermissionsRepository permissionsRepository) {
        this.permissionsRepository = permissionsRepository;
    }

    public Permission checkIfPermissionGranted(long userId, String api) {
        return permissionsRepository.findByUserIdAndApi(userId, api).orElseThrow(PermissionDeniedException::new);
    }
}
