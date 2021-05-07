package com.safayildirim.authservice.dto;

import com.safayildirim.authservice.models.CustomUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginInfoResponse {
    private CustomUser user;
}
