package com.safayildirim.authservice.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPassword {
    @Id
    private String id;
    @NonNull
    private String username;
}
