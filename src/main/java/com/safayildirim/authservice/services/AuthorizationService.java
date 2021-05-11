package com.safayildirim.authservice.services;

import com.safayildirim.authservice.exceptions.PermissionDeniedException;
import com.safayildirim.authservice.models.Permission;
import com.safayildirim.authservice.repos.PermissionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationService {
    private final PermissionsRepository permissionsRepository;

    public Permission checkIfPermissionGranted(long userId, String api) {
        return permissionsRepository.findByUserIdAndApi(userId, api).orElseThrow(PermissionDeniedException::new);
    }
}
