package com.safayildirim.authservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPassword {
    @Id
    private String id;
    @NonNull
    private String username;
    @NonNull
    private LocalDateTime expireDate;
}
