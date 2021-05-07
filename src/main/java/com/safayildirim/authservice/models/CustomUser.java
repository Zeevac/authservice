package com.safayildirim.authservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUser {
    private Long id;
    private String username;
    private String email;
}
