package com.safayildirim.authservice.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {
    @NonNull
    private String username;
    private String password;
}
