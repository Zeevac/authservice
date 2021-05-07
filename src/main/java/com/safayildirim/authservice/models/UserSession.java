package com.safayildirim.authservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSession {
    @Id
    private String sessionID;
    private String username;
    private Date creationDate;
}
